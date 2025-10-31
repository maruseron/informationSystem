package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.domain.Supplier;
import com.maruseron.informationSystem.persistence.SupplierRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("supplier")
public class SupplierController {
    private final SupplierRepository supplierRepository;

    public SupplierController(final SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> get() {
        return ResponseEntity.ok(
                supplierRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> get(@PathVariable Integer id) {
        if (!supplierRepository.existsById(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                supplierRepository.findById(id)
                        .orElseThrow(RuntimeException::new));
    }

    @PostMapping
    public ResponseEntity<Supplier> create(@RequestBody Supplier request)
            throws URISyntaxException {
        final var supplier = supplierRepository.save(request);

        return ResponseEntity.created(
                new URI("/supplier/" + supplier.getId())).body(supplier);
    }

    /*
    // nota: razonar sobre por qué necesitaríamos update para supplier
    @PutMapping("/{id}")
    public ResponseEntity<Supplier> update(@PathVariable Integer id,
                                           @RequestBody Supplier request) {
        var supplier = supplierRepository.findById(id)
                                         .orElseThrow(RuntimeException::new);



        supplierRepository.save(supplier);
        return ResponseEntity.noContent().build();
    }
    */
}