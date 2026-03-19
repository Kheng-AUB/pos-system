package com.kheng.pos.features.product.payload.respone;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreateProductResponse {
    private Long productId;
    private String name;
    private String brand;
    private String sku;
    private String description;
    private Double mrp;
    private Double sellingPrice;
    private String image;
    private Long categoryId;
    private String categoryName;
    private Long storeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
