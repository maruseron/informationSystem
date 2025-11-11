package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.BaseEntity;

public class DtoTypes {
    private DtoTypes() {}

    public interface CreateDto<T extends BaseEntity> {}
    public interface ReadDto<T extends BaseEntity> {
        int id();
        long createdAt();
    }
    public interface UpdateDto<T extends BaseEntity> {}
}
