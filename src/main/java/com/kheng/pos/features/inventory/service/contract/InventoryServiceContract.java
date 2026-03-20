package com.kheng.pos.features.inventory.service.contract;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.inventory.payload.dto.InventoryDto;
import com.kheng.pos.features.inventory.payload.request.CreateInventoryRequest;
import com.kheng.pos.features.inventory.payload.request.UpdateInventoryRequest;
import com.kheng.pos.features.inventory.service.InventoryService;
import com.kheng.pos.features.inventory.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceContract implements InventoryService {
    private final CreateInventoryService createInventoryService;
    private final UpdateInventoryService updateInventoryService;
    private final RemoveInventoryService removeInventoryService;
    private final GetInventoryByIdService getInventoryByIdService;
    private final GetInventoriesByBranchIdService getInventoriesByBranchIdService;
    private final GetInventoryByProductIdAndBranchIdService getInventoryByProductIdAndBranchIdService;

    @Override
    public BaseApiResponse<InventoryDto> createInventory(CreateInventoryRequest request) {
        return createInventoryService.createInventory(request);
    }

    @Override
    public BaseApiResponse<InventoryDto> updateInventory(Long inventoryId, UpdateInventoryRequest request) {
        return updateInventoryService.updateInventory(inventoryId,request);
    }

    @Override
    public BaseApiResponse<Void> deleteInventory(Long inventoryId) {
        return removeInventoryService.deleteInventory(inventoryId);
    }

    @Override
    public BaseApiResponse<InventoryDto> getInventoryById(Long inventoryId) {
        return getInventoryByIdService.getInventoryById(inventoryId);
    }

    @Override
    public BaseApiResponse<InventoryDto> getInventoryByProductIdAndBranchId(Long productId, Long branchId) {
        return getInventoryByProductIdAndBranchIdService.getInventoryByProductIdAndBranchId(productId,branchId);
    }

    @Override
    public BaseApiResponse<List<InventoryDto>> getInventoriesByBranchId(Long branchId) {
        return getInventoriesByBranchIdService.getInventoriesByBranchId(branchId);
    }
}
