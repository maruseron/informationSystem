package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    boolean existsByUsername(final String username);
    boolean existsByNid(final String nid);

    Optional<Employee> findByUsername(final String username);

    Stream<Employee> streamAllBy();
}
