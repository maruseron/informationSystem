package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.domain.Brand;
import com.maruseron.informationSystem.persistence.BrandRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
    private final BrandRepository brandRepository;

    public BrandController(final BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @GetMapping
    public ResponseEntity<List<Brand>> get() {
        return ResponseEntity.ok(
                brandRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> get(@PathVariable Integer id) {
        if (!brandRepository.existsById(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                brandRepository.findById(id)
                        .orElseThrow(RuntimeException::new));
    }

    @PostMapping
    public ResponseEntity<Brand> create(@RequestBody Brand request)
            throws URISyntaxException {
        final var brand = brandRepository.save(request);

        return ResponseEntity.created(
                new URI("/brand/" + brand.getId())).body(brand);
    }

    /*
    // nota: razonar sobre por qué necesitaríamos update para brand
    @PutMapping("/{id}")
    public ResponseEntity<Brand> update(@PathVariable Integer id,
                                        @RequestBody Brand request) {
        var brand = brandRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        brand.setDescription(brand.getDescription());
        brand.setName(brand.getName());

        brandRepository.save(brand);
        return ResponseEntity.noContent().build();
    }
    */
}
