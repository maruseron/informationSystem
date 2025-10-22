package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {}
