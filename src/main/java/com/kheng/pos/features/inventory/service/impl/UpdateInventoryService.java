package com.kheng.pos.features.inventory.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.inventory.entity.Inventory;
import com.kheng.pos.databases.pg.inventory.repository.InventoryRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.inventory.mapper.InventoryMapper;
import com.kheng.pos.features.inventory.payload.dto.InventoryDto;
import com.kheng.pos.features.inventory.payload.request.UpdateInventoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateInventoryService {
    private final InventoryRepository inventoryRepository;

    public BaseApiResponse<InventoryDto> updateInventory(Long inventoryId, UpdateInventoryRequest request) {
        BaseApiResponse<InventoryDto> response = new BaseApiResponse<>();

        // check existing inventory
        Inventory existing = inventoryRepository.findById(inventoryId).orElse(null);
        if (existing == null) {
            throw new AppException("inventory not found",
                    HttpStatus.NOT_FOUND, "0001");
        }

        existing.setQuantity(request.getQuantity());
        existing.setLastUpdate(LocalDateTime.now());
        Inventory updatedInventory = inventoryRepository.save(existing);

        response.setData(InventoryMapper.toDto(updatedInventory));
        response.isSuccess();
        return response;
    }
}
