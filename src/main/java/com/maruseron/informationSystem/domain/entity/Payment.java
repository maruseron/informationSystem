package com.maruseron.informationSystem.domain.entity;

import com.maruseron.informationSystem.domain.enumeration.PaymentMethod;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Payment extends BaseEntity implements Detail<Transaction> {
    /*
    [
        {
            "paymentNid": "...",
            "amount": 23523532525,
            "currency": "VED"
        },
        {
            "paymentNid": "...",
            "amount": 23523532525,
            "currency": "VED"
        },
        {
            "paymentNid": "...",
            "amount": 23523532525,
            "currency": "VED"
        },
     ]
     */
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private PaymentMethod paymentMethod;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    public Payment() {}

    public Payment(int id, Instant createdAt, BigDecimal amount, PaymentMethod paymentMethod,
                   Sale sale) {
        super(id, createdAt);
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.sale = sale;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Payment payment && id == payment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, paymentMethod);
    }
}
