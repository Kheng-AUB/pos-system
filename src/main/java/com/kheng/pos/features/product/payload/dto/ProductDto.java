package com.kheng.pos.features.product.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {
    private Long productId;
    private String name;
    private String brand;
    private String sku;
    private String description;
    private Double mrp;
    private Double sellingPrice;
    private String image;
    private Long categoryId;
    private Long storeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
