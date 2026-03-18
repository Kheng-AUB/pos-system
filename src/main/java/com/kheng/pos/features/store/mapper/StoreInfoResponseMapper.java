package com.kheng.pos.features.store.mapper;

import com.kheng.pos.databases.pg.store.entity.StoreContact;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.features.store.dto.StoreContactDto;
import com.kheng.pos.features.store.dto.StoreInfoDto;
import com.kheng.pos.features.store.dto.StoreInformationDto;
import com.kheng.pos.features.store.dto.response.StoreInfoResponse;

public class StoreInfoResponseMapper {
    public static StoreInfoResponse toStoreInfoResponse(StoreInfo storeInfo, StoreContact storeContact) {

        StoreInfoResponse storeInfoResponse = new StoreInfoResponse();
        StoreInformationDto storeInformationDto = new StoreInformationDto();

        StoreInfoDto storeInfoDto = new StoreInfoDto();
        storeInfoDto.setStoreId(storeInfo.getId());
        storeInfoDto.setBrand(storeInfo.getBrand());
        storeInfoDto.setDescription(storeInfo.getDescription());
        storeInfoDto.setStatus(storeInfo.getStatus());
        storeInfoDto.setStoreType(storeInfo.getStoreType());
        storeInfoDto.setCreatedAt(storeInfo.getCreatedAt());
        storeInfoDto.setUpdatedAt(storeInfo.getUpdatedAt());
        storeInformationDto.setStoreInfo(storeInfoDto);

        StoreContactDto storeContactDto = new StoreContactDto();
        storeContactDto.setPhone(storeContact.getPhone());
        storeContactDto.setEmail(storeContact.getEmail());
        storeContactDto.setAddress(storeContact.getAddress());
        storeInformationDto.setStoreContact(storeContactDto);

        storeInfoResponse.setStoreInformation(storeInformationDto);
        return storeInfoResponse;
    }
}
