package com.kheng.pos.features.inventory.controller;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.inventory.payload.dto.InventoryDto;
import com.kheng.pos.features.inventory.payload.request.CreateInventoryRequest;
import com.kheng.pos.features.inventory.payload.request.UpdateInventoryRequest;
import com.kheng.pos.features.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public BaseApiResponse<InventoryDto> createInventory(
            @RequestBody CreateInventoryRequest request
    ) {
        return inventoryService.createInventory(request);
    }

    @PutMapping("/{inventoryId}")
    public BaseApiResponse<InventoryDto> updateInventory(
            @PathVariable("inventoryId") Long inventoryId,
            @RequestBody UpdateInventoryRequest request
    ) {
        return inventoryService.updateInventory(inventoryId, request);
    }

    @GetMapping("/{inventoryId}")
    public BaseApiResponse<InventoryDto> getInventoryById(
            @PathVariable("inventoryId") Long inventoryId
    ) {
        return inventoryService.getInventoryById(inventoryId);
    }

    @DeleteMapping("/{inventoryId}")
    public BaseApiResponse<Void> deleteInventory(
            @PathVariable("inventoryId") Long inventoryId) {
        return inventoryService.deleteInventory(inventoryId);
    }

    @GetMapping("/branch/{branchId}")
    public BaseApiResponse<List<InventoryDto>> getInventoriesByBranch(
            @PathVariable("branchId") Long branchId
    ) {
        return inventoryService.getInventoriesByBranchId(branchId);
    }

    @GetMapping("/branch/{branchId}/product/{productId}")
    public BaseApiResponse<InventoryDto> getInventoriesByBranch(
            @PathVariable("branchId") Long branchId,
            @PathVariable("productId") Long productId
    ) {
        return inventoryService.getInventoryByProductIdAndBranchId(productId, branchId);
    }

}
