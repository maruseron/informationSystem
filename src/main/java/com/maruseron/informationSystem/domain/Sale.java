package com.maruseron.informationSystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public class Sale extends Transaction {
    @OneToMany
    List<Payment> payments;

    public Sale() {}

    public Sale(int id, Instant createdAt, User user,
                List<TransactionItem> items, List<Payment> payments) {
        super(id, createdAt, user, items);
        this.payments = payments;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Sale sale && id == sale.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(payments);
    }
}
