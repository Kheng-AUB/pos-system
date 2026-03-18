package com.kheng.pos.features.product.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.product.entity.Product;
import com.kheng.pos.databases.pg.product.repository.ProductRepository;
import com.kheng.pos.features.product.mapper.ProductMapper;
import com.kheng.pos.features.product.payload.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchByKeywordService {
    private final ProductRepository productRepository;

    public BaseApiResponse<List<ProductDto>> getProductsByKeyword(Long storeId, String keyword) {
        BaseApiResponse<List<ProductDto>> response = new BaseApiResponse<>();

        List<Product> productList =
                productRepository.searchByKeyword(storeId, keyword);

        response.setData(productList.stream()
                .map(ProductMapper::toDto).toList()
        );
        response.isSuccess();
        return response;
    }
}
