package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface ProductDetailRepository
        extends BaseRepository<ProductDetail> {

    boolean existsByProductId(final int productId);
    Stream<ProductDetail> streamAllByProductId(final int productId);
}
