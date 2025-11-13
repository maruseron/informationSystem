package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.ProductService;
import com.maruseron.informationSystem.application.dto.ProductDTO;
import com.maruseron.informationSystem.domain.entity.Product;
import com.maruseron.informationSystem.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Override
    public String endpoint() {
        return "product";
    }

    @Override
    public ProductService service() {
        return service;
    }
}
