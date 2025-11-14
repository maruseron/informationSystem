package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.BaseEntity;
import com.maruseron.informationSystem.domain.entity.Detail;

public class DtoTypes {
    private DtoTypes() {}

    public interface CreateDto<T extends BaseEntity> {}
    public interface ReadDto<T extends BaseEntity> {
        int id();
        long createdAt();
    }
    public interface UpdateDto<T extends BaseEntity> {}

    public interface DetailCreateDto<
            Master extends BaseEntity,
            T extends BaseEntity & Detail<Master>>
                extends CreateDto<T> {

        DetailCreateDto<Master, T> withMasterId(final int id);
    }
}
