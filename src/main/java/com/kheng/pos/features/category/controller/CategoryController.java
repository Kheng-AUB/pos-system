package com.kheng.pos.features.category.controller;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.category.payload.dto.CategoryDto;
import com.kheng.pos.features.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public BaseApiResponse<CategoryDto> createCategory(
            @RequestBody CategoryDto request)
    {
        return categoryService.createCategory(request);
    }

    @GetMapping("/store/{storeId}")
    public BaseApiResponse<List<CategoryDto>> getCategoriesByStore(
            @PathVariable("storeId") Long storeId
    ) {
        return categoryService.getCategoriesByStoreId(storeId);
    }

    @PutMapping("/{categoryId}")
    public BaseApiResponse<CategoryDto> updateCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody CategoryDto categoryDto
    ){
        return categoryService.updateCategory(categoryId,categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    public BaseApiResponse<Void> deleteCategory(
            @PathVariable("categoryId") Long categoryId
    ){
        return categoryService.deleteCategory(categoryId);
    }


}
