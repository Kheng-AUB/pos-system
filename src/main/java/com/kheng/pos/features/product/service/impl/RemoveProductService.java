package com.kheng.pos.features.product.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.product.entity.Product;
import com.kheng.pos.databases.pg.product.repository.ProductRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveProductService {
    private final ProductRepository productRepository;

    public BaseApiResponse<Void> deleteProduct(Long productId, UserProfileResponse userInfo) {
        BaseApiResponse<Void> response = new BaseApiResponse<>();

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new AppException("Product not found",
                    HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND");
        }
        productRepository.deleteById(productId);
        return response;
    }
}
