package com.kheng.pos.features.inventory.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.inventory.entity.Inventory;
import com.kheng.pos.databases.pg.inventory.repository.InventoryRepository;
import com.kheng.pos.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveInventoryService {
    private final InventoryRepository inventoryRepository;

    public BaseApiResponse<Void> deleteInventory(Long inventoryId) {
        BaseApiResponse<Void> response = new BaseApiResponse<>();

        // check inventory
        // check existing inventory
        Inventory existing = inventoryRepository.findById(inventoryId).orElse(null);
        if (existing == null) {
            throw new AppException("inventory not found",
                    HttpStatus.NOT_FOUND, "0001");
        }

        inventoryRepository.delete(existing);

        response.isSuccess();
        return response;
    }
}
