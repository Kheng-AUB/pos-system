package com.kheng.pos.features.store.mapper;

import com.kheng.pos.databases.pg.store.entity.StoreContact;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.features.auth.dto.StoreContactDto;
import com.kheng.pos.features.auth.dto.StoreInfoDto;
import com.kheng.pos.features.store.dto.response.StoreInfoResponse;

public class StoreInfoResponseMapper {
    public static StoreInfoResponse toStoreInfoResponse(StoreInfo storeInfo, StoreContact storeContact) {
        StoreInfoResponse storeInfoResponse = new StoreInfoResponse();

        StoreInfoDto storeInfoDto = new StoreInfoDto();
        storeInfoDto.setStoreId(storeInfo.getId());
        storeInfoDto.setBrand(storeInfo.getBrand());
        storeInfoDto.setDescription(storeInfo.getDescription());
        storeInfoDto.setStatus(storeInfo.getStatus());
        storeInfoDto.setStoreType(storeInfo.getStoreType());
        storeInfoDto.setCreatedAt(storeInfo.getCreatedAt());
        storeInfoDto.setUpdatedAt(storeInfo.getUpdatedAt());
        storeInfoResponse.setStoreInfo(storeInfoDto);

        StoreContactDto storeContactDto = new StoreContactDto();
        storeContactDto.setStorePhone(storeContact.getPhone());
        storeContactDto.setStoreEmail(storeContact.getEmail());
        storeContactDto.setStoreAddress(storeContact.getAddress());
        storeInfoResponse.setStoreContact(storeContactDto);

        return storeInfoResponse;
    }
}
