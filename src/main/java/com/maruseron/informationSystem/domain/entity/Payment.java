package com.maruseron.informationSystem.domain.entity;

import com.maruseron.informationSystem.domain.enumeration.Currency;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Payment extends BaseEntity {
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
    @Column(nullable = false, unique = true)
    private String nid;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Currency currency;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    public Payment() {}

    public Payment(int id, Instant createdAt, String nid,
                   BigDecimal amount, Currency currency) {
        super(id, createdAt);
        this.nid = nid;
        this.amount = amount;
        this.currency = currency;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
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
        return Objects.hash(id, nid, amount, currency);
    }
}
