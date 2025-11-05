package com.maruseron.informationSystem.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public final class Devolution extends Transaction {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    public Devolution() {}

    public Devolution(int id, Instant date, Employee employee,
                      List<TransactionItem> items, Client client,
                      Sale sale) {
        super(id, date, employee, items);
        this.client = client;
        this.sale = sale;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Devolution devolution && id == devolution.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(client);
    }
}
