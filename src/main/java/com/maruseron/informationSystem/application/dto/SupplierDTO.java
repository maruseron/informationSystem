package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.Supplier;

import java.time.Instant;

public final class SupplierDTO {
    private SupplierDTO() {}
    
    public static Supplier createSupplier(Create spec) {
        return new Supplier(
                0,
                Instant.now(),
                spec.name(),
                spec.nid());
    }

    public static Read fromSupplier(Supplier supplier) {
        return new Read(
                supplier.getId(),
                supplier.getCreatedAt().toEpochMilli(),
                supplier.getName(),
                supplier.getNid());
    }

    public record Create(String name, String nid)
            implements DtoTypes.CreateDto<Supplier> {}

    public record Read(int id, long createdAt, String name, String nid)
            implements DtoTypes.ReadDto<Supplier> {}
}
