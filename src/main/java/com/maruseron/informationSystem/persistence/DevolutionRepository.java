package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.Devolution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevolutionRepository
        extends JpaRepository<Devolution, Integer> {}
