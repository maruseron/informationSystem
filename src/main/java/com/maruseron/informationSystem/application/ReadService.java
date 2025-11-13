package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.DtoTypes;
import com.maruseron.informationSystem.domain.entity.BaseEntity;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.BaseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReadService<
        T extends BaseEntity,
        Read extends DtoTypes.ReadDto<T>,
        Repository extends BaseRepository<T>> {

    Read toDTO(final T entity);

    Repository repository();

    @Transactional(readOnly = true)
    default Either<Read, HttpResult> findById(final int id) {
        return repository()
                .findById(id)
                .map(this::toDTO)
                .<Either<Read, HttpResult>>map(Either::left)
                .orElseGet(() -> Either.right(new HttpResult(HttpStatus.NOT_FOUND)));
    }

    @Transactional(readOnly = true)
    default List<Read> findAll() {
        try (final var items = repository().streamAllBy()) {
            return items.map(this::toDTO).toList();
        }
    }
}
