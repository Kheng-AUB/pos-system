package com.kheng.pos.features.product.service.contract;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.product.payload.dto.ProductDto;
import com.kheng.pos.features.product.payload.request.CreateProductRequest;
import com.kheng.pos.features.product.payload.request.UpdateProductRequest;
import com.kheng.pos.features.product.payload.respone.CreateProductResponse;
import com.kheng.pos.features.product.service.ProductService;
import com.kheng.pos.features.product.service.impl.*;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceContract implements ProductService {
    private final GetProductByStoreIdService getProductByStoreIdService;
    private final SearchByKeywordService searchByKeywordService;
    private final CreateProductService createProductService;
    private final RemoveProductService removeProductService;
    private final UpdateProductService updateProductService;

    @Override
    public BaseApiResponse<CreateProductResponse> createProduct(CreateProductRequest request, UserProfileResponse userInfo) {
        return createProductService.createProduct(request, userInfo);
    }

    @Override
    public BaseApiResponse<ProductDto> updateProduct(Long productId, UpdateProductRequest request, UserProfileResponse userInfo) {
        return updateProductService.updateProduct(productId,request, userInfo);
    }

    @Override
    public BaseApiResponse<Void> deleteProduct(Long productId, UserProfileResponse userInfo) {
        return removeProductService.deleteProduct(productId, userInfo);
    }

    @Override
    public BaseApiResponse<List<ProductDto>> getProductsByStoreId(Long storeId) {
        return getProductByStoreIdService.getProductsByStoreId(storeId);
    }

    @Override
    public BaseApiResponse<List<ProductDto>> searchByKeyword(Long storeId, String keyword) {
        return searchByKeywordService.getProductsByKeyword(storeId, keyword);
    }
}
