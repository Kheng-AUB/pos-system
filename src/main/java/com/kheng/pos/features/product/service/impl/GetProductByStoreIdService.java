package com.kheng.pos.features.product.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.product.entity.Product;
import com.kheng.pos.databases.pg.product.repository.ProductRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.product.mapper.ProductMapper;
import com.kheng.pos.features.product.payload.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetProductByStoreIdService {
    private final ProductRepository productRepository;

    public BaseApiResponse<List<ProductDto>> getProductsByStoreId(Long storeId) {
        BaseApiResponse<List<ProductDto>> response = new BaseApiResponse<>();

        List<Product> products = productRepository.findByStoreId(storeId);
        if (products == null || products.isEmpty()) {
            throw new AppException("Product not found",
                    HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND");
        }

        response.setData(products.stream()
                .map(ProductMapper::toDto).toList()
        );
        response.isSuccess();
        return response;
    }
}
