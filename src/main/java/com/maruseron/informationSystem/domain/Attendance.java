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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Attendance() {}

    public Attendance(int id, Instant createdAt, Instant startTime,
                      int hours, User user) {
        super(id, createdAt);
        this.startTime = startTime;
        this.hours = hours;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Attendance attendance && id == attendance.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, startTime, user);
    }
}
