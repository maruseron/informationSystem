package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.Product;
import com.maruseron.informationSystem.domain.entity.ProductDetail;

import java.time.Instant;

public final class ProductDetailDTO {
    private ProductDetailDTO() {}

    public static ProductDetail createProductDetail(Create spec, Product product) {
        return new ProductDetail(
                0,
                Instant.now(),
                product,
                spec.sku(),
                spec.stock(),
                spec.size(),
                spec.color());
    }

    public static Read fromProductDetail(ProductDetail productDetail) {
        return new Read(
                productDetail.getId(),
                productDetail.getCreatedAt().toEpochMilli(),
                productDetail.getSku(),
                productDetail.getStock(),
                productDetail.getSize(),
                productDetail.getColor());
    }

    public record Create(int productId, String sku, int stock, int size, String color)
            implements DtoTypes.DetailCreateDto<Product, ProductDetail> {

        public Create withMasterId(final int id) {
            return new Create(id, sku(), stock(), size(), color());
        }
    }

    public record Read(int id, long createdAt, String sku, int stock, int size, String color)
            implements DtoTypes.ReadDto<ProductDetail> {}

    public record Update(int stock)
            implements DtoTypes.UpdateDto<ProductDetail> {}
}
