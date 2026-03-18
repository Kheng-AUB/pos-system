package com.kheng.pos.features.store.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.store.entity.StoreContact;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.databases.pg.store.repository.StoreContactRepository;
import com.kheng.pos.databases.pg.store.repository.StoreInfoRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveStoreService {
    private final StoreContactRepository storeContactRepository;
    private final StoreInfoRepository storeInfoRepository;
    private final UserInfoService userInfoService;

    public BaseApiResponse<Void> deleteStore() {
        BaseApiResponse<Void> response = new BaseApiResponse<>();

        UserProfileResponse currentUser = userInfoService.getCurrentUserInfo().getData();
        StoreInfo storeInfo = storeInfoRepository.findById(currentUser.getUserId()).orElse(null);
        if (storeInfo == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        StoreContact storeContact = storeContactRepository.findByStoreId(storeInfo.getId());
        if (storeContact == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        storeInfoRepository.delete(storeInfo);
        storeInfoRepository.delete(storeInfo);
        response.isSuccess();
        return response;
    }
}
