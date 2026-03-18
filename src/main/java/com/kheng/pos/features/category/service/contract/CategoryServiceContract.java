package com.kheng.pos.features.category.service.contract;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.category.payload.dto.CategoryDto;
import com.kheng.pos.features.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceContract implements CategoryService {
    @Override
    public BaseApiResponse<CategoryDto> createCategory(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public BaseApiResponse<CategoryDto> updateCategory(Long categoryId, CategoryDto categoryDto) {
        return null;
    }

    @Override
    public BaseApiResponse<List<CategoryDto>> getCategoriesByStoreId(Long storeId) {
        return null;
    }

    @Override
    public BaseApiResponse<Void> deleteCategory(Long categoryId) {
        return null;
    }
}
