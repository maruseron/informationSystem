package com.maruseron.informationSystem.dto;

import com.maruseron.informationSystem.domain.Employee;

public final class EmployeeDTO {
    private EmployeeDTO() {}

    public record Create(String username, String password, String firstName, String lastName,
                         String nid, String role) {}

    public record Read(int id, String username, String firstName, String lastName) {

        public static Read from(Employee employee) {
            return new Read(employee.getId(), employee.getUsername(),
                            employee.getFirstName(), employee.getLastName());
        }
    }

    public record Update(String username, String password, String firstName, String lastName,
                         String role) {}

    }