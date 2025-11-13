package com.maruseron.informationSystem.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.Instant;
import java.util.Objects;

@Entity
public class Supplier extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nid;

    public Supplier() {}

    public Supplier(int id, Instant createdAt, String name, String nid) {
        super(id, createdAt);
        this.name = name;
        this.nid = nid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Supplier supplier && id == supplier.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nid);
    }
}
