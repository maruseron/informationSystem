package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository
        extends JpaRepository<ProductDetail, Integer> {}
