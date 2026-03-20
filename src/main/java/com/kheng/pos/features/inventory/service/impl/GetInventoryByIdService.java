package com.kheng.pos.features.inventory.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.inventory.entity.Inventory;
import com.kheng.pos.databases.pg.inventory.repository.InventoryRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.inventory.mapper.InventoryMapper;
import com.kheng.pos.features.inventory.payload.dto.InventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetInventoryByIdService {
    private final InventoryRepository inventoryRepository;

    public BaseApiResponse<InventoryDto> getInventoryById(Long inventoryId) {
        BaseApiResponse<InventoryDto> response = new BaseApiResponse<>();

        // check inventory
        Inventory inventory = inventoryRepository.findById(inventoryId).orElse(null);
        if (inventory == null) {
            throw new AppException("inventory not found",
                    HttpStatus.NOT_FOUND, "0001");
        }

        response.setData(InventoryMapper.toDto(inventory));
        response.isSuccess();
        return response;
    }
}
