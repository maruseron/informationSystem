package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.domain.entity.Transaction;
import com.maruseron.informationSystem.persistence.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    private final TransactionRepository transactionRepository;

    public TransactionController(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> get() {
        return ResponseEntity.ok(
                transactionRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> get(@PathVariable Integer id) {
        if (!transactionRepository.existsById(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                transactionRepository.findById(id)
                        .orElseThrow(RuntimeException::new));
    }
}
