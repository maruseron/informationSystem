package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {}
