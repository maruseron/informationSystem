package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {}
