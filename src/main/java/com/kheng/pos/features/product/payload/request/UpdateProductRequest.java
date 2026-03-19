package com.kheng.pos.features.product.payload.request;

import lombok.Data;

@Data
public class UpdateProductRequest {
    private String name;
    private String brand;
    private String description;
    private Double mrp;
    private Double sellingPrice;
    private String image;
}
