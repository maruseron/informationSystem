package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.Brand;

import java.time.Instant;

public final class BrandDTO {
    private BrandDTO() {}

    public static Brand createBrand(Create spec) {
        return new Brand(
                0,
                Instant.now(),
                spec.name());
    }

    public static Read fromBrand(Brand brand) {
        return new Read(
                brand.getId(),
                brand.getCreatedAt().toEpochMilli(),
                brand.getName());
    }

    public record Create(String name)
            implements DtoTypes.CreateDto<Brand> {}

    public record Read(int id, long createdAt, String name)
            implements DtoTypes.ReadDto<Brand> {}
}