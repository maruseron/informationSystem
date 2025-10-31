package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.domain.Devolution;
import com.maruseron.informationSystem.domain.Purchase;
import com.maruseron.informationSystem.persistence.EmployeeRepository;
import com.maruseron.informationSystem.persistence.PurchaseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("purchase")
public class PurchaseController {
    private final EmployeeRepository employeeRepository;
    private final PurchaseRepository purchaseRepository;

    public PurchaseController(final EmployeeRepository employeeRepository,
                              final PurchaseRepository purchaseRepository) {
        this.employeeRepository = employeeRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> get() {
        return ResponseEntity.ok(
                purchaseRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> get(@PathVariable Integer id) {
        if (!purchaseRepository.existsById(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                purchaseRepository.findById(id)
                        .orElseThrow(RuntimeException::new));
    }

    @PostMapping
    public ResponseEntity<Purchase> create(@RequestBody Purchase request)
            throws URISyntaxException {
        // we extract the employee id from the request body and look for an
        // employee of same id in the database - once extracted, set this
        // non-detached object as the request's employee object, then save
        final var employee = employeeRepository
                .findById(request.getEmployee().getId())
                .orElseThrow(RuntimeException::new);
        request.setEmployee(employee);
        final var purchase = purchaseRepository.save(request);

        return ResponseEntity.created(
                new URI("/purchase/" + purchase.getId())).body(purchase);
    }
}
