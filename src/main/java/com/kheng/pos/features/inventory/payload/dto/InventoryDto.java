package com.kheng.pos.features.inventory.payload.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryDto {
    Long inventoryId;
    Long branchId;
    Long productId;
    Integer quantity;
    LocalDateTime lastUpdate;
}
