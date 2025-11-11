package com.maruseron.informationSystem.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
public class ProductDetail extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false)
    private String color;

    public ProductDetail() {}

    public ProductDetail(int id, Instant createdAt, Product product, int stock,
                         int size, String color) {
        super(id, createdAt);
        this.product = product;
        this.stock = stock;
        this.size = size;
        this.color = color;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof ProductDetail productDetail && id == productDetail.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock, size, product, color);
    }
}
