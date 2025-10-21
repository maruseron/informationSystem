package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.Product;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity
public interface ProductRepository extends JpaRepository<Product, Integer> {}
