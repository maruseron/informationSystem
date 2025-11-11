package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.domain.entity.BaseEntity;
import com.maruseron.informationSystem.application.dto.DtoTypes;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CreateService<
        T extends BaseEntity,
        Create extends DtoTypes.CreateDto<T>,
        Read extends DtoTypes.ReadDto<T>,
        Repository extends BaseRepository<T>> extends ReadService<T, Read, Repository> {

    T fromDTO(final Create spec);

    /**
     * Ensures an entry creation spec is valid.
     * @param request the creation spec
     * @return an Either object containing the unmodified creation spec, or an HttpResult
     * describing the error.
     */
    Either<Create, HttpResult> validateForCreation(final Create request);

    @Transactional
    default Either<Read, HttpResult> create(final Create request) {
        return validateForCreation(request)
                .map(this::fromDTO)
                .map(repository()::save)
                .map(this::toDTO);
    }
}
