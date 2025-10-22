package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Employee, Integer> {}
