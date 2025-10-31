package com.maruseron.informationSystem.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.Instant;
import java.util.Objects;

@Entity
public class Employee extends Base {
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
	private String firstName;
    @Column(nullable = false)
	private String lastName;

    @Column(nullable = false, unique = true)
    private String nid;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
	private Role role;

	private Employee() {}

    public Employee(int id, Instant createdAt, String username, String password,
                    String firstName, String lastName, String nid, Role role) {
        super(id, createdAt);
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nid = nid;
        this.role = role;
    }

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

    @Override
	public boolean equals(Object o) {
		return o instanceof Employee employee && id == employee.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nid, firstName, lastName,
                            username, password, role);
	}
}
