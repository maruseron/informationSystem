package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.Employee;
import com.maruseron.informationSystem.domain.enumeration.Role;

import java.time.Instant;

public final class EmployeeDTO {
    private EmployeeDTO() {}

    public static Employee createEmployee(Create spec) {
        return new Employee(
                0,
                Instant.now(),
                spec.username(),
                spec.password(),
                spec.firstName(),
                spec.lastName(),
                spec.nid(),
                Role.valueOf(spec.role().toUpperCase()));
    }

    public static Read fromEmployee(Employee employee) {
        return new Read(
                employee.getId(),
                employee.getCreatedAt().toEpochMilli(),
                employee.getUsername(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getNid(),
                employee.getRole().toString());
    }

    public record Create(String username, String password, String firstName, String lastName,
                         String nid, String role)
            implements DtoTypes.CreateDto<Employee> {}

    public record Read(int id, long createdAt, String username, String firstName, String lastName,
                       String nid, String role)
            implements DtoTypes.ReadDto<Employee> {}

    public record Update(String username, String password, String firstName, String lastName,
                         String role)
            implements DtoTypes.UpdateDto<Employee> {}
}