package com.kheng.pos.features.store.dto.request;

import com.kheng.pos.features.auth.dto.StoreContactDto;
import lombok.Data;

@Data
public class StoreInfoRequest {
    private String brand;
    private String description;
    private String storeType;
    private String status;
    private StoreContactDto storeContact;
}
