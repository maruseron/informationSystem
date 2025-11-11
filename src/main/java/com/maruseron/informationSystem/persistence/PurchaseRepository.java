package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {}
