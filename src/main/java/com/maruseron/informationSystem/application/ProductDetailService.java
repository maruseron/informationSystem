package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.ProductDetailDTO;
import com.maruseron.informationSystem.domain.entity.ProductDetail;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.ProductDetailRepository;
import com.maruseron.informationSystem.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailService implements
    CreateService<ProductDetail, ProductDetailDTO.Create, ProductDetailDTO.Read,
                  ProductDetailRepository>,
    UpdateService<ProductDetail, ProductDetailDTO.Update, ProductDetailDTO.Read,
                  ProductDetailRepository>
{
    @Autowired
    ProductDetailRepository repository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductDetailRepository repository() {
        return repository;
    }

    @Override
    public ProductDetail fromDTO(ProductDetailDTO.Create spec) {
        return ProductDetailDTO.createProductDetail(
                spec,
                // validateForCreation ensures this call is safe
                productRepository.findById(spec.productId()).orElseThrow());
    }

    @Override
    public ProductDetailDTO.Read toDTO(ProductDetail entity) {
        return null;
    }

    @Override
    public Either<ProductDetailDTO.Create, HttpResult> validateForCreation(
            ProductDetailDTO.Create request) {
        return null;
    }

    @Override
    public Either<ProductDetail, HttpResult> validateAndUpdate(ProductDetail entity, ProductDetailDTO.Update request) {
        return null;
    }
}
