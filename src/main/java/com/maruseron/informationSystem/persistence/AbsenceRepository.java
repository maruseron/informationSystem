package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository
        extends JpaRepository<Absence, Integer>, StreamableRepository<Absence> {}
