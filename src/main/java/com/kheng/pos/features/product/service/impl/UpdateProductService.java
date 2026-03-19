package com.kheng.pos.features.product.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.product.entity.Product;
import com.kheng.pos.databases.pg.product.repository.ProductRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.product.mapper.ProductMapper;
import com.kheng.pos.features.product.payload.dto.ProductDto;
import com.kheng.pos.features.product.payload.request.UpdateProductRequest;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.kheng.pos.core.util.GlobalUtils.isBlank;

@Service
@RequiredArgsConstructor
public class UpdateProductService {
    private final ProductRepository productRepository;

    public BaseApiResponse<ProductDto> updateProduct(Long productId, UpdateProductRequest request, UserProfileResponse userInfo) {
        BaseApiResponse<ProductDto> response = new BaseApiResponse<>();

        // check if product exist
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new AppException("Product not found",
                    HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND");
        }

        if (!isBlank(request.getName())) {
            product.setName(request.getName());
        }
        if (!isBlank(request.getDescription())) {
            product.setDescription(request.getDescription());
        }
        if (!isBlank(request.getBrand())) {
            product.setBrand(request.getBrand());
        }
        if (!isBlank(request.getImage())) {
            product.setImage(request.getImage());
        }

        if (request.getMrp() != null) {
            product.setMrp(request.getMrp());
        }
        if (request.getSellingPrice() != null) {
            product.setSellingPrice(request.getSellingPrice());
        }

        product.setUpdatedAt(LocalDateTime.now());
        Product updatedProduct = productRepository.save(product);

        response.setData(ProductMapper.toDto(updatedProduct));
        response.isSuccess();
        return response;
    }
}
