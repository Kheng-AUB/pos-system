package com.kheng.pos.features.store.dto;

import com.kheng.pos.features.auth.dto.StoreContactDto;
import com.kheng.pos.features.auth.dto.UserInformationDto;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class StoreInformationDto {

    private Long storeId;
    private String brand;
    private String description;
    private String storeType;
    private String status;

    private StoreContactDto storeContactDto;
    private UserInformationDto storeAdmin;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
