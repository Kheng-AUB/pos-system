package com.kheng.pos.features.category.payload.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private Long storeId;
}
