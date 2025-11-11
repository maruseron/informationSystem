package com.maruseron.informationSystem.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "brand")
public class Brand extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    public Brand() {}

    public Brand(int id, Instant createdAt, String name, String description) {
        super(id, createdAt);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Brand brand && id == brand.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
