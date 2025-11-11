package com.maruseron.informationSystem.domain.entity;

import com.maruseron.informationSystem.domain.enumeration.PermissionStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.Instant;
import java.util.Objects;

@Entity
public class Absence extends BaseEntity {
    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private PermissionStatus permissionStatus;

    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private int duration;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supervisor_id", nullable = true)
    private Employee supervisor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "requester_id", nullable = false)
    private Employee requester;

    private Absence() {}

    public Absence(int id, Instant createdAt,
                   String reason, PermissionStatus permissionStatus,
                   Instant startTime, int duration,
                   Employee supervisor, Employee requester) {
        super(id, createdAt);
        this.reason = reason;
        this.permissionStatus = permissionStatus;
        this.startTime = startTime;
        this.duration = duration;
        this.supervisor = supervisor;
        this.requester = requester;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public PermissionStatus getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(PermissionStatus permissionStatus) {
        this.permissionStatus = permissionStatus;
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

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public Employee getRequester() {
        return requester;
    }

    public void setRequester(Employee requester) {
        this.requester = requester;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Absence absence && id == absence.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reason, permissionStatus,
                            duration, startTime, supervisor, requester);
    }
}
