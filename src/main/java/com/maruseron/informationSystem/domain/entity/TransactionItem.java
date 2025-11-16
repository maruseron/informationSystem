package com.maruseron.informationSystem.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
public class TransactionItem extends BaseEntity implements Detail<Transaction> {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_detail_id", nullable = false)
    private ProductDetail productDetail;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal discount;

    TransactionItem() {}

    public TransactionItem(int id, Instant createdAt,
                           Transaction transaction,
                           ProductDetail productDetail,
                           BigDecimal amount,
                           int quantity) {
        super(id, createdAt);
        this.transaction = transaction;
        this.productDetail = productDetail;
        this.amount = amount;
        this.quantity = quantity;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
