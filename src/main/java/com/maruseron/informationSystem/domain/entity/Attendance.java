package com.maruseron.informationSystem.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "attendance")
public class Attendance extends BaseEntity {
    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private int duration;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public Attendance() {}

    public Attendance(int id, Instant createdAt, Instant startTime,
                      int duration, Employee employee) {
        super(id, createdAt);
        this.startTime = startTime;
        this.duration = duration;
        this.employee = employee;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
        return Objects.hash(duration, startTime, employee);
    }
}
