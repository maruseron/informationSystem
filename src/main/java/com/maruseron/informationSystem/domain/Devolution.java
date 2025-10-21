package com.maruseron.informationSystem.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public class Devolution extends Transaction {
    @Column(nullable = false)
    private String devolverId;

    public Devolution() {}

    public Devolution(int id, Instant date, User user,
                      List<TransactionItem> items, String devolverId) {
        super(id, date, user, items);
        this.devolverId = devolverId;
    }

    public String getDevolverId() {
        return devolverId;
    }

    public void setDevolverId(String devolverId) {
        this.devolverId = devolverId;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Devolution devolution && id == devolution.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(devolverId);
    }
}
