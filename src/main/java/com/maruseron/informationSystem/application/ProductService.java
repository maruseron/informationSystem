package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.ProductDTO;
import com.maruseron.informationSystem.domain.entity.Product;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.BrandRepository;
import com.maruseron.informationSystem.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService implements
    CreateService<Product, ProductDTO.Create, ProductDTO.Read, ProductRepository>,
    UpdateService<Product, ProductDTO.Update, ProductDTO.Read, ProductRepository>
{
    @Autowired
    ProductRepository repository;

    @Autowired
    BrandRepository brandRepository;

    @Override
    public ProductRepository repository() {
        return repository;
    }

    @Override
    public Product fromDTO(ProductDTO.Create spec) {
        return ProductDTO.createProduct(
                spec,
                // validateForCreation ensures this call is safe
                brandRepository.findById(spec.brandId()).orElseThrow());
    }

    @Override
    public ProductDTO.Read toDTO(Product entity) {
        return ProductDTO.fromProduct(entity);
    }

    @Override
    public Either<ProductDTO.Create, HttpResult> validateForCreation(ProductDTO.Create request) {
        if (!brandRepository.existsById(request.brandId()))
            return Either.right(new HttpResult(HttpStatus.NOT_FOUND));

        return Either.left(request);
    }

    @Override
    public Either<Product, HttpResult> validateAndUpdate(Product entity, ProductDTO.Update request) {
        entity.setBuyingPrice(new BigDecimal(request.buyingPrice()));
        entity.setSellingPrice(new BigDecimal(request.sellingPrice()));
        return Either.left(entity);
    }


}
