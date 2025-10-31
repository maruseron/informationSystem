package com.maruseron.informationSystem.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Payment extends Base {
    @Column(nullable = false, unique = true)
    private String paymentNid;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    public Payment() {}

    public Payment(int id, Instant createdAt, String paymentNid,
                   BigDecimal amount, Currency currency) {
        super(id, createdAt);
        this.paymentNid = paymentNid;
        this.amount = amount;
        this.currency = currency;
    }

    public String getPaymentNid() {
        return paymentNid;
    }

    public void setPaymentNid(String paymentNid) {
        this.paymentNid = paymentNid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Payment payment && id == payment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentNid, amount, currency);
    }
}
