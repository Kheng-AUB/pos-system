package com.kheng.pos.features.product.service.contract;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.product.payload.dto.ProductDto;
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
    public BaseApiResponse<ProductDto> createProduct(ProductDto productDto, UserProfileResponse userInfo) {
        return createProductService.createProduct(productDto, userInfo);
    }

    @Override
    public BaseApiResponse<ProductDto> updateProduct(Long productId,ProductDto productDto, UserProfileResponse userInfo) {
        return updateProductService.updateProduct(productId,productDto, userInfo);
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
