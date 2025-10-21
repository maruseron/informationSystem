package com.maruseron.informationSystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public class Purchase extends Transaction {
    @ManyToOne
    private Supplier supplier;

    public Purchase() {}

    public Purchase(int id, Instant createdAt, User user,
                    List<TransactionItem> items, Supplier supplier) {
        super(id, createdAt, user, items);
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Purchase purchase && id == purchase.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(supplier);
    }
}
