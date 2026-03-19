package com.kheng.pos.features.product.mapper;

import com.kheng.pos.databases.pg.cateogry.entity.Category;
import com.kheng.pos.databases.pg.product.entity.Product;
import com.kheng.pos.features.product.payload.dto.ProductDto;
import com.kheng.pos.features.product.payload.request.CreateProductRequest;
import com.kheng.pos.features.product.payload.respone.CreateProductResponse;

import java.time.LocalDateTime;

public class ProductMapper {
    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getId());
        productDto.setName(product.getName());
        productDto.setCategoryId(product.getCategoryId());
        productDto.setImage(product.getImage());
        productDto.setStoreId(product.getStoreId());
        productDto.setBrand(product.getBrand());
        productDto.setDescription(product.getDescription());
        productDto.setMrp(product.getMrp());
        productDto.setSellingPrice(product.getSellingPrice());
        productDto.setSku(product.getSku());
        productDto.setCreatedAt(product.getCreatedAt());
        productDto.setUpdatedAt(product.getUpdatedAt());

        return productDto;
    }
    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setMrp(productDto.getMrp());
        product.setSellingPrice(productDto.getSellingPrice());
        product.setCategoryId(productDto.getCategoryId());
        product.setImage(productDto.getImage());
        product.setStoreId(productDto.getStoreId());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return product;
    }


    public static Product toCreateProduct(CreateProductRequest request){
        Product product = new Product();
        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBrand(request.getBrand());
        product.setMrp(request.getMrp());
        product.setSellingPrice(request.getSellingPrice());
        product.setImage(request.getImage());
        product.setCategoryId(request.getCategoryId());
        product.setStoreId(request.getStoreId());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return product;
    }

    public static CreateProductResponse toCreateProductResponse(Product savedProduct, Category category){
        CreateProductResponse productResponse = new CreateProductResponse();
        productResponse.setProductId(savedProduct.getId());
        productResponse.setName(savedProduct.getName());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setBrand(savedProduct.getBrand());
        productResponse.setMrp(savedProduct.getMrp());
        productResponse.setSellingPrice(savedProduct.getSellingPrice());
        productResponse.setSku(savedProduct.getSku());
        productResponse.setImage(savedProduct.getImage());
        productResponse.setStoreId(savedProduct.getStoreId());
        productResponse.setCategoryId(savedProduct.getCategoryId());
        productResponse.setCategoryName(category.getName());
        productResponse.setCreatedAt(savedProduct.getCreatedAt());
        productResponse.setUpdatedAt(savedProduct.getUpdatedAt());

        return productResponse;
    }
}
