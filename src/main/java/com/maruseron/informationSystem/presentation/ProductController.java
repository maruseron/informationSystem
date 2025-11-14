package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.ProductDetailService;
import com.maruseron.informationSystem.application.ProductService;
import com.maruseron.informationSystem.application.dto.ProductDTO;
import com.maruseron.informationSystem.application.dto.ProductDetailDTO;
import com.maruseron.informationSystem.domain.entity.Product;
import com.maruseron.informationSystem.domain.entity.ProductDetail;
import com.maruseron.informationSystem.persistence.ProductDetailRepository;
import com.maruseron.informationSystem.persistence.ProductRepository;
import com.maruseron.informationSystem.util.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("product")
public class ProductController implements
        CreateController<Product, ProductDTO.Create, ProductDTO.Read,
                         ProductRepository, ProductService>,
        UpdateController<Product, ProductDTO.Update, ProductDTO.Read,
                         ProductRepository, ProductService>
{
    @Autowired
    ProductService service;

    @Autowired
    ProductDetailService productDetailService;

    @Override
    public String endpoint() {
        return "product";
    }

    @Override
    public ProductService service() {
        return service;
    }

    // DETAILS:

    @GetMapping("/detail/{productId}")
    public ResponseEntity<?> getDetails(@PathVariable int productId) {
        return ResponseEntity.ok(productDetailService.findAllDetailsFor(productId));
    }

    @GetMapping("/detail/unfiltered/{id}")
    public ResponseEntity<?> getDetail(@PathVariable int id) {
        return Controllers.handleResult(
                productDetailService.findById(id),
                ResponseEntity::ok);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> createDetail(@PathVariable int id,
                                          @RequestBody ProductDetailDTO.Create request) {
        return Controllers.handleResult(
                productDetailService.create(request.withMasterId(id)),
                detail -> ResponseEntity.created(
                        new URI("/" + endpoint() + "/detail/unfiltered/" + detail.id())).body(detail));
    }

    @PutMapping("/detail/unfiltered/{id}")
    public ResponseEntity<?> updateDetail(@PathVariable int id,
                                          @RequestBody ProductDetailDTO.Update request) {
        return Controllers.handleResult(
                productDetailService.update(id, request),
                _ -> ResponseEntity.noContent().build());
    }
}
