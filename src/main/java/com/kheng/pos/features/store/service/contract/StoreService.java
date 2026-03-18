package com.kheng.pos.features.store.service.contract;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.store.dto.request.StoreInfoRequest;
import com.kheng.pos.features.store.dto.response.StoreInfoResponse;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;

import java.util.List;

public interface StoreService {
    BaseApiResponse<StoreInfoResponse> createStore(StoreInfoRequest request, UserProfileResponse userInfo);
    BaseApiResponse<StoreInfoResponse> getStoreById(Long id);
    BaseApiResponse<List<StoreInfoResponse>> getAllStores();
    BaseApiResponse<StoreInfoResponse> getStoreByAdmin();
    BaseApiResponse<StoreInfoResponse>  updateStore(Long id,StoreInfoRequest request);
    BaseApiResponse<Void> deleteStore(Long id);
    BaseApiResponse<StoreInfoResponse> getStoreByEmployee();

    BaseApiResponse<StoreInfoResponse> moderateStore(Long id, String status);
}
