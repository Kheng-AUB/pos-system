package com.kheng.pos.features.category.service.contract;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.category.payload.dto.CategoryDto;
import com.kheng.pos.features.category.service.CategoryService;
import com.kheng.pos.features.category.service.impl.CreateCategoryService;
import com.kheng.pos.features.category.service.impl.GetCategoriesByStoreIdService;
import com.kheng.pos.features.category.service.impl.RemoveCategoryService;
import com.kheng.pos.features.category.service.impl.UpdateCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceContract implements CategoryService {
    private final CreateCategoryService createCategoryService;
    private final GetCategoriesByStoreIdService getCategoriesByStoreIdService;
    private final RemoveCategoryService removeCategoryService;
    private final UpdateCategoryService updateCategoryService;

    @Override
    public BaseApiResponse<CategoryDto> createCategory(CategoryDto categoryDto) {
        return createCategoryService.createCategory(categoryDto);
    }

    @Override
    public BaseApiResponse<CategoryDto> updateCategory(Long categoryId, CategoryDto categoryDto){
        return updateCategoryService.updateCategory(categoryId, categoryDto);
    }

    @Override
    public BaseApiResponse<List<CategoryDto>> getCategoriesByStoreId(Long storeId) {
        return getCategoriesByStoreIdService.getCategoriesByStoreId(storeId);
    }

    @Override
    public BaseApiResponse<Void> deleteCategory(Long categoryId){
        return removeCategoryService.deleteCategory(categoryId);
    }
}
