package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {}
