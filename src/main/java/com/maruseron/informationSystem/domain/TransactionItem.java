package com.maruseron.informationSystem.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
public class TransactionItem extends Base {
    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "product_detail_id", nullable = false)
    private ProductDetail productDetail;

    private int quantity;

    private BigDecimal discount;

    TransactionItem() {}

    public TransactionItem(int id, Instant createdAt,
                           Transaction transaction,
                           ProductDetail productDetail, int quantity,
                           BigDecimal discount) {
        super(id, createdAt);
        this.transaction = transaction;
        this.productDetail = productDetail;
        this.quantity = quantity;
        this.discount = discount;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public final boolean sameProductVariation(TransactionItem other) {
        return this.getProductDetail().getId() == other.getProductDetail().getId();
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof TransactionItem transItem && id == transItem.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, transaction, productDetail, discount);
    }
}
