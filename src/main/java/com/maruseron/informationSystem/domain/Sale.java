package com.maruseron.informationSystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public final class Sale extends Transaction {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sale")
    List<Payment> payments;

    public Sale() {}

    public Sale(int id, Instant createdAt, Employee employee,
                List<TransactionItem> items, List<Payment> payments) {
        super(id, createdAt, employee, items);
        this.payments = payments;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Sale sale && id == sale.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(payments);
    }
}
