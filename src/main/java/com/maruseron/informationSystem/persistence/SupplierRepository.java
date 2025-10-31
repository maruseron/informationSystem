package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {}
