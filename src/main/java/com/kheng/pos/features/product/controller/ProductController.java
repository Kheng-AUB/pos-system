package com.kheng.pos.features.product.controller;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.product.payload.dto.ProductDto;
import com.kheng.pos.features.product.payload.request.CreateProductRequest;
import com.kheng.pos.features.product.payload.request.UpdateProductRequest;
import com.kheng.pos.features.product.payload.respone.CreateProductResponse;
import com.kheng.pos.features.product.service.ProductService;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    private final UserInfoService userInfoService;

    @PostMapping("/create")
    public BaseApiResponse<CreateProductResponse> createProduct(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateProductRequest request) {
        UserProfileResponse userInfo =
                userInfoService.getUserInfoFromJwtToken(token).getData();

        return productService.createProduct(request, userInfo);
    }

    @GetMapping("/store/{storeId}")
    public BaseApiResponse<List<ProductDto>> getProductByStore(
            @PathVariable("storeId") Long storeId,
            @RequestHeader("Authorization") String token
    ) {
        return productService.getProductsByStoreId(storeId);
    }

    @GetMapping("/store/{storeId}/search")
    public BaseApiResponse<List<ProductDto>> searchByKeyword(
            @PathVariable("storeId") Long storeId,
            @RequestHeader("Authorization") String token,
            @RequestParam("keyword") String keyword
    ) {
        return productService.searchByKeyword(storeId, keyword);
    }

    @PutMapping("/{productId}")
    public BaseApiResponse<ProductDto> updateProduct(
            @PathVariable("productId") Long productId,
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateProductRequest request
    ) {
        UserProfileResponse userInfo =
                userInfoService.getUserInfoFromJwtToken(token).getData();

        return productService.updateProduct(productId, request, userInfo);
    }

    @DeleteMapping("{productId}")
    public BaseApiResponse<Void> deleteProduct(
            @PathVariable("productId") Long productId,
            @RequestHeader("Authorization") String token
    ) {
        UserProfileResponse userInfo =
                userInfoService.getUserInfoFromJwtToken(token).getData();

        return productService.deleteProduct(productId, userInfo);
    }
}
