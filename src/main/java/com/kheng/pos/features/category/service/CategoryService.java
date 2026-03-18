package com.kheng.pos.features.category.service;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.category.payload.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    BaseApiResponse<CategoryDto> createCategory(CategoryDto categoryDto);

    BaseApiResponse<CategoryDto> updateCategory(Long categoryId, CategoryDto categoryDto);

    BaseApiResponse<List<CategoryDto>> getCategoriesByStoreId(Long storeId);

    BaseApiResponse<Void> deleteCategory(Long categoryId);
}
