package com.kheng.pos.features.store.dto.response;

import com.kheng.pos.features.auth.dto.StoreContactDto;
import com.kheng.pos.features.auth.dto.StoreInfoDto;
import lombok.Data;

@Data
public class StoreInfoResponse {
    private StoreInfoDto storeInfo;
    private StoreContactDto storeContact;
}
