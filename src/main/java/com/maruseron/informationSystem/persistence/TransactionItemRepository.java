package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionItemRepository
        extends JpaRepository<TransactionItem, Integer> {}
