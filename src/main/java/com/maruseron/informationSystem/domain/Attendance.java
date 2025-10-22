package com.maruseron.informationSystem.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "attendance")
public class Attendance extends Base {
    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private int hours;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public Attendance() {}

    public Attendance(int id, Instant createdAt, Instant startTime,
                      int hours, Employee employee) {
        super(id, createdAt);
        this.startTime = startTime;
        this.hours = hours;
        this.employee = employee;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public Employee getUser() {
        return employee;
    }

    public void setUser(Employee employee) {
        this.employee = employee;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Attendance attendance && id == attendance.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, startTime, employee);
    }
}
