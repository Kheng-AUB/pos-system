package com.kheng.pos.features.inventory.mapper;

import com.kheng.pos.databases.pg.inventory.entity.Inventory;
import com.kheng.pos.features.inventory.payload.dto.InventoryDto;
import com.kheng.pos.features.inventory.payload.request.CreateInventoryRequest;

import java.time.LocalDateTime;

public class InventoryMapper {
    public static Inventory toEntity(CreateInventoryRequest request){
        if(request == null){
            return null;
        }

        Inventory inventory = new Inventory();
        inventory.setBranchId(request.getBranchId());
        inventory.setProductId(request.getProductId());
        inventory.setQuantity(request.getQuantity());
        inventory.setLastUpdate(LocalDateTime.now());

        return inventory;
    }

    public static InventoryDto toDto(Inventory inventory){
        InventoryDto dto = new InventoryDto();
        if(inventory == null){
            return null;
        }

        dto.setInventoryId(inventory.getInventoryId());
        dto.setBranchId(inventory.getBranchId());
        dto.setProductId(inventory.getProductId());
        dto.setQuantity(inventory.getQuantity());
        dto.setLastUpdate(inventory.getLastUpdate());

        return dto;
    }
}
