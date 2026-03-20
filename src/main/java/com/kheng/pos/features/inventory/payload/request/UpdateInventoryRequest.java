package com.kheng.pos.features.inventory.payload.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateInventoryRequest {
    Integer quantity;
}
