package com.kheng.pos.features.category.mapper;

import com.kheng.pos.databases.pg.cateogry.entity.Category;
import com.kheng.pos.features.category.payload.dto.CategoryDto;

import java.time.LocalDateTime;

public class CategoryMapper {
    public static Category toEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getCategoryName());
        category.setStoreId(categoryDto.getStoreId());
        category.setCreatedAt(LocalDateTime.now());
        return category;
    }

    public static CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getId());
        categoryDto.setCategoryName(category.getName());
        categoryDto.setStoreId(category.getStoreId());
        return categoryDto;
    }
}
