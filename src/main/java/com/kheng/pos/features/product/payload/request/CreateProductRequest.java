package com.kheng.pos.features.product.payload.request;

import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private String brand;
    private String sku;
    private String description;
    private Double mrp;
    private Double sellingPrice;
    private String image;
    private Long categoryId;
    private Long storeId;
}
