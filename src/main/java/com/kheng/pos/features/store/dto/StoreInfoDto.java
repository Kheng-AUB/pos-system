package com.kheng.pos.features.store.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreInfoDto {
    private Long storeId;
    private String brand;
    private String description;
    private String storeType;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
