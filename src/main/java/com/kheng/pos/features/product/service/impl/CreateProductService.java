package com.kheng.pos.features.product.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.cateogry.entity.Category;
import com.kheng.pos.databases.pg.cateogry.repository.CategoryRepository;
import com.kheng.pos.databases.pg.product.entity.Product;
import com.kheng.pos.databases.pg.product.repository.ProductRepository;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.databases.pg.store.repository.StoreInfoRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.product.mapper.ProductMapper;
import com.kheng.pos.features.product.payload.dto.ProductDto;
import com.kheng.pos.features.product.payload.request.CreateProductRequest;
import com.kheng.pos.features.product.payload.respone.CreateProductResponse;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateProductService {
    private final ProductRepository productRepository;
    private final StoreInfoRepository storeInfoRepository;
    private final CategoryRepository categoryRepository;

    public BaseApiResponse<CreateProductResponse> createProduct(CreateProductRequest request, UserProfileResponse userInfo) {
        BaseApiResponse<CreateProductResponse> response = new BaseApiResponse<>();

        // check store
        StoreInfo storeInfo = storeInfoRepository.findById(request.getStoreId()).orElse(null);
        if (storeInfo == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        // check category
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            throw new AppException("Category not found",
                    HttpStatus.NOT_FOUND, "CATEGORY_NOT_FOUND");
        }

        Product product = ProductMapper.toCreateProduct(request);
        Product savedProduct = productRepository.save(product);

        CreateProductResponse productResponse = ProductMapper.toCreateProductResponse(savedProduct,category);

        response.setData(productResponse);
        response.isSuccess();
        return response;
    }
}
