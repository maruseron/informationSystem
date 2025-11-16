package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.entity.ProductDetail;
import com.maruseron.informationSystem.domain.entity.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface TransactionItemRepository
        extends BaseRepository<TransactionItem> {

    boolean existsByTransactionId(final int productId);
    Stream<TransactionItem> streamAllByTransactionId(final int productId);
}
