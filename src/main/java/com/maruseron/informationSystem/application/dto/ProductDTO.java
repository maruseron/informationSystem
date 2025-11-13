package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.Brand;
import com.maruseron.informationSystem.domain.entity.Product;

import java.math.BigDecimal;
import java.time.Instant;

public final class ProductDTO {
    private ProductDTO() {}

    public static Product createProduct(Create spec, Brand brand) {
        return new Product(
                0,
                Instant.now(),
                spec.name(),
                spec.description(),
                new BigDecimal(spec.buyingPrice()),
                new BigDecimal(spec.sellingPrice()),
                brand);
    }

    public static Read fromProduct(Product product) {
        return new Read(
                product.getId(),
                product.getCreatedAt().toEpochMilli(),
                product.getName(),
                product.getDescription(),
                product.getBuyingPrice().toString(),
                product.getSellingPrice().toString(),
                BrandDTO.fromBrand(product.getBrand()));
    }

    public record Create(String name, String description, String buyingPrice, String sellingPrice,
                         int brandId)
            implements DtoTypes.CreateDto<Product> {}

    public record Read(int id, long createdAt, String name, String description, String buyingPrice,
                       String sellingPrice, BrandDTO.Read brand)
            implements DtoTypes.ReadDto<Product> {}

    public record Update(String buyingPrice, String sellingPrice)
            implements DtoTypes.UpdateDto<Product> {}
}
