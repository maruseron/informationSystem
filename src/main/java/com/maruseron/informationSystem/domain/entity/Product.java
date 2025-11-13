package com.maruseron.informationSystem.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal buyingPrice;

    @Column(nullable = false)
    private BigDecimal sellingPrice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    public Product() {}

    public Product(int id, Instant createdAt, String name, String description,
                   BigDecimal buyingPrice, BigDecimal sellingPrice, Brand brand) {
        super(id, createdAt);
        this.name = name;
        this.description = description;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
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

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
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
        return Objects.hash(id, name, description, sellingPrice, brand);
    }
}
