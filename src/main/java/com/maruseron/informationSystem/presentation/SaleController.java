package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.domain.Sale;
import com.maruseron.informationSystem.persistence.EmployeeRepository;
import com.maruseron.informationSystem.persistence.SaleRepository;
import com.maruseron.informationSystem.presentation.dto.SaleCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("sale")
public class SaleController {
    private final EmployeeRepository employeeRepository;
    private final SaleRepository saleRepository;

    public SaleController(final EmployeeRepository employeeRepository,
                          final SaleRepository saleRepository) {
        this.employeeRepository = employeeRepository;
        this.saleRepository = saleRepository;
    }

    @GetMapping
    public ResponseEntity<List<Sale>> get() {
        return ResponseEntity.ok(
                saleRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> get(@PathVariable Integer id) {
        if (!saleRepository.existsById(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                saleRepository.findById(id)
                        .orElseThrow(RuntimeException::new));
    }

    record PaymentCreateRequest() {}

    @PostMapping
    public ResponseEntity<Sale> create(@RequestBody SaleCreateRequest request)
            throws URISyntaxException {


        request.payments().stream().forEach(paymentService::register);


        /*
        final var employee = employeeRepository
                .findById(request.getEmployee().getId())
                .orElseThrow(RuntimeException::new);
        request.setEmployee(employee);
        final var sale = saleRepository.save(request);


         */
        return ResponseEntity.created(
                new URI("/sale/" + sale.getId())).body(sale);
    }
}
