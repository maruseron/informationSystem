package com.maruseron.informationSystem.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public final class Sale extends Transaction {
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sale", cascade = CascadeType.ALL)
    List<Payment> payments;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public Sale() {}

    public Sale(int id, Instant createdAt, Employee employee,
                List<TransactionItem> items, List<Payment> payments,
                Client client) {
        super(id, createdAt, employee, items);
        this.payments = payments;
        this.client = client;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Sale sale && id == sale.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(payments, client);
    }
}
