package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.TransactionDTO;
import com.maruseron.informationSystem.domain.entity.Transaction;
import com.maruseron.informationSystem.persistence.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class TransactionService
    implements ReadService<Transaction, TransactionDTO.Read, TransactionRepository> {

    @Autowired
    TransactionRepository repository;

    @Override
    public TransactionDTO.Read toDTO(Transaction entity) {
        return TransactionDTO.fromTransaction(entity);
    }

    @Override
    public TransactionRepository repository() {
        return repository;
    }
}
