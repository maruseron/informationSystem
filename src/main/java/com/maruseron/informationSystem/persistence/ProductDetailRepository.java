package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProductDetailRepository
        extends BaseRepository<ProductDetail> {

    boolean existsBySku(final String sku);
    boolean existsByProductId(final int productId);

    Optional<ProductDetail> findBySku(final String sku);

    Stream<ProductDetail> streamAllByProductId(final int productId);
}
