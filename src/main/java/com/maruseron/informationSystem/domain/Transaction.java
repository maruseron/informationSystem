package com.maruseron.informationSystem.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction extends Base {
    @Column(nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "transaction")
    private List<TransactionItem> items;

    public Transaction() {}

    public Transaction(int id, Instant createdAt, User user,
                       List<TransactionItem> items) {
        super(id, createdAt);
        this.user = user;
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TransactionItem> getItems() {
        return items;
    }

    public void setItems(List<TransactionItem> items) {
        this.items = items;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Transaction t)) return false;
        return id == t.id
                && Objects.equals(date, t.date)
                && Objects.equals(user, t.user)
                && Objects.equals(items, t.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, user, items);
    }
     */
}
