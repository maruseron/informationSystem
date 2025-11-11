package com.maruseron.informationSystem.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "brand")
public class Brand extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;

    public Brand() {}

    public Brand(int id, Instant createdAt, String name) {
        super(id, createdAt);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Brand brand && id == brand.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
