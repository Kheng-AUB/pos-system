package com.kheng.pos.features.inventory.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.branch.entity.Branch;
import com.kheng.pos.databases.pg.branch.repository.BranchRepository;
import com.kheng.pos.databases.pg.inventory.entity.Inventory;
import com.kheng.pos.databases.pg.inventory.repository.InventoryRepository;
import com.kheng.pos.databases.pg.product.entity.Product;
import com.kheng.pos.databases.pg.product.repository.ProductRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.inventory.mapper.InventoryMapper;
import com.kheng.pos.features.inventory.payload.dto.InventoryDto;
import com.kheng.pos.features.inventory.payload.request.CreateInventoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateInventoryService {
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public BaseApiResponse<InventoryDto> createInventory(CreateInventoryRequest request) {
        BaseApiResponse<InventoryDto> response = new BaseApiResponse<>();

        // check branch
        Branch branch = branchRepository.findById(request.getBranchId()).orElse(null);
        if (branch == null) {
            throw new AppException("branch not found",
                    HttpStatus.NOT_FOUND, "BRANCH_NOT_FOUND");
        }

        // check product
        Product product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            throw new AppException("product not found",
                    HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND");
        }

        Inventory inventory = InventoryMapper.toEntity(request);
        Inventory savedInventory = inventoryRepository.save(inventory);

        response.setData(InventoryMapper.toDto(savedInventory));
        response.isSuccess();
        return response;
    }
}
