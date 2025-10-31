package com.maruseron.informationSystem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "client")
public class Client extends Base {
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private Client nid;

    @Column(nullable = false)
    private String address;

    public Client() {}

    public Client(int id, Instant createdAt, String fullName, Client nid,
                  String address) {
        super(id, createdAt);
        this.fullName = fullName;
        this.nid = nid;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Client getNid() {
        return nid;
    }

    public void setNid(Client nid) {
        this.nid = nid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof Client c && id == c.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, nid, address);
    }
}
