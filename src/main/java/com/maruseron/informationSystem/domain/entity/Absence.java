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
    @JoinColumn(name = "authorizer_id", nullable = true)
    private Employee authorizer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private Absence() {}

    public Absence(int id, Instant createdAt,
                   String reason, PermissionStatus permissionStatus,
                   Instant startTime, int duration,
                   Employee authorizer, Employee employee) {
        super(id, createdAt);
        this.reason = reason;
        this.permissionStatus = permissionStatus;
        this.startTime = startTime;
        this.duration = duration;
        this.authorizer = authorizer;
        this.employee = employee;
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

    public Employee getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(Employee authorizer) {
        this.authorizer = authorizer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Absence absence && id == absence.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reason, permissionStatus,
                            duration, startTime, authorizer, employee);
    }
}
