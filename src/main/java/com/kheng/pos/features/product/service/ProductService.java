package com.kheng.pos.features.product.service;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.product.payload.dto.ProductDto;
import com.kheng.pos.features.product.payload.request.CreateProductRequest;
import com.kheng.pos.features.product.payload.request.UpdateProductRequest;
import com.kheng.pos.features.product.payload.respone.CreateProductResponse;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;

import java.util.List;

public interface ProductService {
    BaseApiResponse<CreateProductResponse> createProduct(CreateProductRequest request, UserProfileResponse userInfo);

    BaseApiResponse<ProductDto> updateProduct(Long productId, UpdateProductRequest request, UserProfileResponse userInfo);

    BaseApiResponse<Void> deleteProduct(Long productId, UserProfileResponse userInfo);

    BaseApiResponse<List<ProductDto>> getProductsByStoreId(Long storeId);

    BaseApiResponse<List<ProductDto>> searchByKeyword(Long storeId, String keyword);
}
