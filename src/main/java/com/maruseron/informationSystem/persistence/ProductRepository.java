package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.entity.Product;
import com.maruseron.informationSystem.domain.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface ProductRepository extends BaseRepository<Product> {}
