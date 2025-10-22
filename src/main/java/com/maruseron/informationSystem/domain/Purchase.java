package com.maruseron.informationSystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public final class Purchase extends Transaction {
    @ManyToOne
    private Supplier supplier;

    public Purchase() {}

    public Purchase(int id, Instant createdAt, Employee employee,
                    List<TransactionItem> items, Supplier supplier) {
        super(id, createdAt, employee, items);
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Purchase purchase && id == purchase.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(supplier);
    }
}
