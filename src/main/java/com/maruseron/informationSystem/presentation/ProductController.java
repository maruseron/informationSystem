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
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Integer id) {
        return productRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product request)
            throws URISyntaxException {
        final var product = productRepository.save(request);
        return ResponseEntity.created(
                new URI("/user/" + product.getId())).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id,
                                                 @RequestBody Product request) {
        var product =
                productRepository.findById(id).orElseThrow(RuntimeException::new);
        product.setBrand(request.getBrand());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product = productRepository.save(request);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
