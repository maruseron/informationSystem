package com.maruseron.informationSystem.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public final class Devolution extends Transaction {
    @Column(nullable = false)
    private String devolverId;

    public Devolution() {}

    public Devolution(int id, Instant date, Employee employee,
                      List<TransactionItem> items, String devolverId) {
        super(id, date, employee, items);
        this.devolverId = devolverId;
    }

    public String getDevolverId() {
        return devolverId;
    }

    public void setDevolverId(String devolverId) {
        this.devolverId = devolverId;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Devolution devolution && id == devolution.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(devolverId);
    }
}
