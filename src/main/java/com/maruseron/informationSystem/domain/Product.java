package com.maruseron.informationSystem.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Product extends Base {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    public Product() {}

    public Product(int id, Instant createdAt, String name, String description,
                   BigDecimal price, Brand brand) {
        super(id, createdAt);
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Product product && id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, brand);
    }
}
