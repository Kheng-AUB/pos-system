package com.kheng.pos.features.product.mapper;

import com.kheng.pos.databases.pg.product.entity.Product;
import com.kheng.pos.features.product.payload.dto.ProductDto;

import java.time.LocalDateTime;

public class ProductMapper {
    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setCategoryId(product.getCategoryId());
        productDto.setImage(product.getImage());
        productDto.setStoreId(product.getStoreId());
        productDto.setBrand(product.getBrand());
        productDto.setDescription(product.getDescription());
        productDto.setMrp(product.getMrp());
        productDto.setSellingPrice(product.getSellingPrice());
        productDto.setSku(product.getSku());
        productDto.setCreatedAt(product.getCreatedAt());
        productDto.setUpdatedAt(product.getUpdatedAt());

        return productDto;
    }
    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setMrp(productDto.getMrp());
        product.setSellingPrice(productDto.getSellingPrice());
        product.setCategoryId(productDto.getCategoryId());
        product.setImage(productDto.getImage());
        product.setStoreId(productDto.getStoreId());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return product;
    }
}
