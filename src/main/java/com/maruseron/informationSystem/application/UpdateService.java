package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.DtoTypes;
import com.maruseron.informationSystem.domain.entity.BaseEntity;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.BaseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

public interface UpdateService<
        T extends BaseEntity,
        Update extends DtoTypes.UpdateDto<T>,
        Read extends DtoTypes.ReadDto<T>,
        Repository extends BaseRepository<T>> extends ReadService<T, Read, Repository> {

    /**
     * Updates the fields in an entity according to the provided spec. It is responsible to ensure
     * the validity of the requested updates.
     * @param entity the entity to update
     * @param spec the update spec
     * @return an Either object containing the updated entity, or an HttpResult describing the
     * error.
     */
    Either<T, HttpResult> validateAndUpdate(T entity, Update spec);

    @Transactional
    default Either<Read, HttpResult> update(final int id,
                                            final Update request) {
        return repository()
                .findById(id)
                .map(x -> validateAndUpdate(x, request))
                .orElseGet(() -> Either.right(new HttpResult(HttpStatus.NOT_FOUND)))
                .map(repository()::save)
                .map(this::toDTO);
    }
}
