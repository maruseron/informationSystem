package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.persistence.StreamableRepository;
import com.maruseron.informationSystem.util.Either;
import com.maruseron.informationSystem.util.HttpResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class BaseService<T, C, R, U,
        Repository extends StreamableRepository<T> & JpaRepository<T, Integer>> {
    Repository repository;

    abstract T fromDTO(final C spec);

    abstract R toDTO(final T entity);

    /**
     * Ensures an entry creation spec is valid.
     * @param request the creation spec
     * @return an Either object containing the unmodified creation spec, or an HttpResult
     * describing the error.
     */
    abstract Either<C, HttpResult> validateForCreation(final C request);

    @Transactional
    public Either<R, HttpResult> create(final C request) {
        return validateForCreation(request)
                .map(this::fromDTO)
                .map(repository::save)
                .map(this::toDTO);
    }

    public Either<R, HttpResult> findById(final int id) {
        return repository
                .findById(id)
                .map(this::toDTO)
                .<Either<R, HttpResult>>map(Either::left)
                .orElseGet(() -> Either.right(new HttpResult(HttpStatus.NOT_FOUND)));
    }

    @Transactional
    public List<R> findAll() {
        try (final var items = repository.streamAllBy()) {
            return items.map(this::toDTO).toList();
        }
    }

    /**
     * Updates the fields in an entity according to the provided spec. It is responsible to ensure
     * the validity of the requested updates.
     * @param entity the entity to update
     * @param spec the update spec
     * @return an Either object containing the updated entity, or an HttpResult describing the
     * error.
     */
    abstract Either<T, HttpResult> updateFields(T entity, U spec);

    @Transactional
    public Either<R, HttpResult> update(final int id,
                                        final U request) {
        return repository
                .findById(id)
                .map(x -> updateFields(x, request))
                .orElseGet(() -> Either.right(new HttpResult(HttpStatus.NOT_FOUND)))
                .map(repository::save)
                .map(this::toDTO);
    }
}
