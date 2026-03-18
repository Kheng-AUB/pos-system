package com.kheng.pos.features.store.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.store.entity.StoreContact;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.databases.pg.store.repository.StoreContactRepository;
import com.kheng.pos.databases.pg.store.repository.StoreInfoRepository;
import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.entity.UserStore;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.databases.pg.userinfo.repository.UserStoreRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.store.dto.response.StoreInfoResponse;
import com.kheng.pos.features.store.mapper.StoreInfoResponseMapper;
import com.kheng.pos.features.store.service.util.StoreServiceUtil;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetStoreService {
    private final UserInformationRepository userInformationRepository;
    private final StoreContactRepository storeContactRepository;
    private final StoreInfoRepository storeInfoRepository;
    private final UserStoreRepository userStoreRepository;
    private final UserInfoService userInfoService;

    public BaseApiResponse<StoreInfoResponse> getStoreById(Long id) {
        BaseApiResponse<StoreInfoResponse> response = new BaseApiResponse<>();

        if (id == null) {
            throw new AppException("Invalid store ID",
                    HttpStatus.BAD_REQUEST, "INVALID_ID");
        }

        StoreInfo storeInfo = storeInfoRepository.findById(id).orElse(null);
        if (storeInfo == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        StoreContact storeContact =
                StoreServiceUtil.checkStoreContact(storeInfo.getId(), storeContactRepository);

        StoreInfoResponse storeInfoResponse =
                StoreInfoResponseMapper.toStoreInfoResponse(storeInfo, storeContact);
        response.setData(storeInfoResponse);
        response.isSuccess();
        return response;
    }

    public BaseApiResponse<List<StoreInfoResponse>> getAllStores() {
        BaseApiResponse<List<StoreInfoResponse>> response = new BaseApiResponse<>();

        List<StoreInfo> stores = storeInfoRepository.findAll();
        for (StoreInfo storeInfo : stores) {
            StoreContact storeContact = storeContactRepository.findByStoreId(storeInfo.getId());
            if (storeContact == null) {
                throw new AppException("Store not found",
                        HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
            }

            response.getData().add(StoreInfoResponseMapper.toStoreInfoResponse(storeInfo, storeContact));
        }

        response.isSuccess();
        return response;
    }

    public BaseApiResponse<StoreInfoResponse> getStoreByAdmin() {
        BaseApiResponse<StoreInfoResponse> response = new BaseApiResponse<>();

        UserProfileResponse admin = userInfoService.getCurrentUserInfo().getData();
        UserStore userStore = userStoreRepository.findTopByUserIdOrderByIdDesc(admin.getUserId());

        StoreInfo store = storeInfoRepository.findTopByIdOrderByIdDesc(userStore.getStoreId());
        if (store == null) {
            throw new AppException("Store not found for admin",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        StoreContact storeContact = storeContactRepository.findByStoreId(store.getId());
        if (storeContact == null) {
            throw new AppException("Store not found for admin",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        StoreInfoResponse storeInfoResponse =
                StoreInfoResponseMapper.toStoreInfoResponse(store, storeContact);

        response.setData(storeInfoResponse);
        response.isSuccess();

        return response;
    }

    public BaseApiResponse<StoreInfoResponse> getStoreByEmployee() {
        BaseApiResponse<StoreInfoResponse> response = new BaseApiResponse<>();

        UserProfileResponse employee = userInfoService.getCurrentUserInfo().getData();
        UserInformation userInformation = userInformationRepository.findByEmail(employee.getEmail());

        StoreInfo storeInfo = storeInfoRepository.findById(userInformation.getId()).orElse(null);
        if (storeInfo == null) {
            throw new AppException("Employee has no associated store",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }
        StoreContact storeContact = storeContactRepository.findByStoreId(storeInfo.getId());
        if (storeContact == null) {
            throw new AppException("Employee has no associated store",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        StoreInfoResponse storeInfoResponse =
                StoreInfoResponseMapper.toStoreInfoResponse(storeInfo, storeContact);

        response.setData(storeInfoResponse);
        response.isSuccess();
        return response;
    }
}
