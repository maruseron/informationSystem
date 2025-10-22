package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {}
