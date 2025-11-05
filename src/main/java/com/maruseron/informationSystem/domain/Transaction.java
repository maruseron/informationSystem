package com.maruseron.informationSystem.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "transaction")
@Inheritance(strategy = InheritanceType.JOINED)
public class Transaction extends Base {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", nullable = false)
    protected Employee employee;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "transaction", cascade = CascadeType.ALL)
    protected List<TransactionItem> items;

    public Transaction() {}

    public Transaction(int id, Instant createdAt, Employee employee,
                       List<TransactionItem> items) {
        super(id, createdAt);
        this.employee = employee;
        this.items = items;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
