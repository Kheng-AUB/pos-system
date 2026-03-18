package com.kheng.pos.features.store.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.store.entity.StoreContact;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.databases.pg.store.repository.StoreContactRepository;
import com.kheng.pos.databases.pg.store.repository.StoreInfoRepository;
import com.kheng.pos.databases.pg.userinfo.entity.UserStore;
import com.kheng.pos.databases.pg.userinfo.repository.UserStoreRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.store.dto.request.StoreInfoRequest;
import com.kheng.pos.features.store.dto.response.StoreInfoResponse;
import com.kheng.pos.features.store.mapper.StoreInfoResponseMapper;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateStoreService {
    private final StoreInfoRepository storeInfoRepository;
    private final StoreContactRepository storeContactRepository;
    private final UserStoreRepository userStoreRepository;

    public BaseApiResponse<StoreInfoResponse> createStore(StoreInfoRequest storeInfoRequest,
                                                          UserProfileResponse userInfo) {

        BaseApiResponse<StoreInfoResponse> response = new BaseApiResponse<>();

        if (storeInfoRequest == null) {
            throw new AppException("Store data is required",
                    HttpStatus.BAD_REQUEST, "INVALID_DATA");
        }

        if (userInfo == null) {
            throw new AppException("User information is required",
                    HttpStatus.BAD_REQUEST, "INVALID_USER");
        }

        StoreInfo storeInfo = buildStoreInfo(storeInfoRequest);
        StoreInfo savedStore = storeInfoRepository.save(storeInfo);

        StoreContact storeContact = buildStoreContact(storeInfoRequest, savedStore.getId());
        storeContactRepository.save(storeContact);

        // Insert into user_store
        UserStore userStore = new UserStore();
        userStore.setUserId(userInfo.getUserId());
        userStore.setStoreId(storeInfo.getId());
        userStore.setCreatedAt(LocalDateTime.now());
        userStoreRepository.save(userStore);

        StoreInfoResponse storeInfoResponse =
                StoreInfoResponseMapper.toStoreInfoResponse(storeInfo, storeContact);

        response.setData(storeInfoResponse);
        response.isSuccess();
        return response;
    }

    private StoreContact buildStoreContact(StoreInfoRequest storeInfoRequest, Long storeId) {
        StoreContact storeContact = new StoreContact();
        storeContact.setStoreId(storeId);
        storeContact.setPhone(storeInfoRequest.getStoreContact().getPhone());
        storeContact.setEmail(storeInfoRequest.getStoreContact().getEmail());
        storeContact.setAddress(storeInfoRequest.getStoreContact().getAddress());
        return storeContact;
    }

    private StoreInfo buildStoreInfo(StoreInfoRequest storeInfoRequest) {
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setBrand(storeInfoRequest.getBrand());
        storeInfo.setDescription(storeInfoRequest.getDescription());
        storeInfo.setStoreType(storeInfoRequest.getStoreType());
        storeInfo.setStatus("PENDING");
        storeInfo.setCreatedAt(LocalDateTime.now());
        storeInfo.setUpdatedAt(LocalDateTime.now());
        return storeInfo;
    }
}
