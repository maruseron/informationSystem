package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.ProductDetailDTO;
import com.maruseron.informationSystem.domain.entity.ProductDetail;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.ProductDetailRepository;
import com.maruseron.informationSystem.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return ProductDetailDTO.fromProductDetail(entity);
    }

    @Transactional
    public Either<List<ProductDetailDTO.Read>, HttpResult> findAllDetailsFor(int id) {
        if (!productRepository.existsById(id))
            return Either.right(new HttpResult(
                    HttpStatus.NOT_FOUND,
                    "El producto solicitado no existe."));

        try (final var productVariants = repository.streamAllByProductId(id)) {
            final var list = productVariants.map(this::toDTO).toList();

            return list.isEmpty()
                    ? Either.right(new HttpResult(
                    HttpStatus.NOT_FOUND,
                    "No se ha registrado ninguna variante para el producto indicado."))
                    : Either.left(list);
        }
    }

    @Override
    public Either<ProductDetailDTO.Create, HttpResult> validateForCreation(
            ProductDetailDTO.Create request) {
        return productRepository.existsById(request.productId())
                ? Either.left(request)
                : Either.right(new HttpResult(HttpStatus.NOT_FOUND));
    }

    @Override
    public Either<ProductDetail, HttpResult> validateAndUpdate(
            ProductDetail entity, ProductDetailDTO.Update request) {
        entity.setStock(request.stock());
        return Either.left(entity);
    }
}
