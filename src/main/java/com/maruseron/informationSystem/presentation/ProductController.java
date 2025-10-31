package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.domain.Product;
import com.maruseron.informationSystem.persistence.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<List<Product>> get() {
        return ResponseEntity.ok(
                productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Integer id) {
        if (!productRepository.existsById(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                productRepository.findById(id)
                                 .orElseThrow(RuntimeException::new));
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product request)
            throws URISyntaxException {
        final var product = productRepository.save(request);

        return ResponseEntity.created(
                new URI("/product/" + product.getId())).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Integer id,
                                          @RequestBody Product request) {
        var product = productRepository.findById(id)
                                       .orElseThrow(RuntimeException::new);

        product.setBrand(request.getBrand());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        productRepository.save(product);
        return ResponseEntity.noContent().build();
    }
}
