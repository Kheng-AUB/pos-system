package com.kheng.pos.features.inventory.service;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.inventory.payload.dto.InventoryDto;
import com.kheng.pos.features.inventory.payload.request.CreateInventoryRequest;
import com.kheng.pos.features.inventory.payload.request.UpdateInventoryRequest;

import java.util.List;

public interface InventoryService {
    BaseApiResponse<InventoryDto> createInventory(CreateInventoryRequest request);
    BaseApiResponse<InventoryDto> updateInventory(Long inventoryId, UpdateInventoryRequest request);
    BaseApiResponse<Void> deleteInventory(Long inventoryId);
    BaseApiResponse<InventoryDto> getInventoryById(Long inventoryId);
    BaseApiResponse<InventoryDto> getInventoryByProductIdAndBranchId(Long productId, Long branchId);
    BaseApiResponse<List<InventoryDto>> getInventoriesByBranchId(Long branchId);
}
