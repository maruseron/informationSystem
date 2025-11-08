package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.Employee;

import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<Employee> {
    boolean existsByUsername(final String username);
    boolean existsByNid(final String nid);

    Optional<Employee> findByUsername(final String username);
}
