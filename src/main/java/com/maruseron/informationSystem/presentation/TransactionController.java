package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.TransactionItemService;
import com.maruseron.informationSystem.application.TransactionService;
import com.maruseron.informationSystem.application.dto.TransactionDTO;
import com.maruseron.informationSystem.application.dto.TransactionItemDTO;
import com.maruseron.informationSystem.domain.entity.Transaction;
import com.maruseron.informationSystem.persistence.TransactionRepository;
import com.maruseron.informationSystem.util.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("transaction")
public class TransactionController
    implements ReadController<Transaction, TransactionDTO.Read,
                              TransactionRepository, TransactionService> {

    @Autowired
    TransactionService service;

    @Autowired
    TransactionItemService transactionItemService;

    @Override
    public TransactionService service() {
        return service;
    }

    // DETAILS:

    @GetMapping("/detail/{transactionId}")
    public ResponseEntity<?> getDetails(@PathVariable int transactionId) {
        return ResponseEntity.ok(transactionItemService.findAllDetailsFor(transactionId));
    }

    @GetMapping("/detail/unfiltered/{id}")
    public ResponseEntity<?> getDetail(@PathVariable int id) {
        return Controllers.handleResult(
                transactionItemService.findById(id),
                ResponseEntity::ok);
    }
}
