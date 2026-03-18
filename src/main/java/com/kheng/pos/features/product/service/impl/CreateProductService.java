package com.kheng.pos.features.product.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.product.entity.Product;
import com.kheng.pos.databases.pg.product.repository.ProductRepository;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.databases.pg.store.repository.StoreInfoRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.product.mapper.ProductMapper;
import com.kheng.pos.features.product.payload.dto.ProductDto;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductService {
    private final ProductRepository productRepository;
    private final StoreInfoRepository storeInfoRepository;

    public BaseApiResponse<ProductDto> createProduct(ProductDto productDto, UserProfileResponse userInfo) {
        BaseApiResponse<ProductDto> response = new BaseApiResponse<>();

        // check store
        StoreInfo storeInfo = storeInfoRepository.findById(productDto.getStoreId()).orElse(null);
        if (storeInfo == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        Product product = ProductMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);

        response.setData(ProductMapper.toDto(savedProduct));
        response.isSuccess();
        return response;
    }
}
