package com.kheng.pos.features.category.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.cateogry.entity.Category;
import com.kheng.pos.databases.pg.cateogry.repository.CategoryRepository;
import com.kheng.pos.features.category.mapper.CategoryMapper;
import com.kheng.pos.features.category.payload.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCategoriesByStoreIdService {
    private final CategoryRepository categoryRepository;

    public BaseApiResponse<List<CategoryDto>> getCategoriesByStoreId(Long storeId) {
        BaseApiResponse<List<CategoryDto>> response = new BaseApiResponse<>();
        List<Category> categories = categoryRepository.findByStoreId(storeId);

        response.setData(categories.stream()
                .map(CategoryMapper::toDto).toList());
        response.isSuccess();
        return response;
    }
}
