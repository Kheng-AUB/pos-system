package com.kheng.pos.features.store.service.contract;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.store.dto.request.StoreInfoRequest;
import com.kheng.pos.features.store.dto.response.StoreInfoResponse;
import com.kheng.pos.features.store.service.StoreService;
import com.kheng.pos.features.store.service.impl.CreateStoreService;
import com.kheng.pos.features.store.service.impl.GetStoreService;
import com.kheng.pos.features.store.service.impl.ModifyStoreService;
import com.kheng.pos.features.store.service.impl.RemoveStoreService;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceContract implements StoreService {
    private final CreateStoreService createStoreService;
    private final GetStoreService getStoreService;
    private final ModifyStoreService modifyStoreService;
    private final RemoveStoreService removeStoreService;

    @Override
    public BaseApiResponse<StoreInfoResponse> createStore(StoreInfoRequest request, UserProfileResponse userInfo) {
        return createStoreService.createStore(request, userInfo);
    }

    @Override
    public BaseApiResponse<StoreInfoResponse> getStoreById(Long id) {
        return getStoreService.getStoreById(id);
    }

    @Override
    public BaseApiResponse<List<StoreInfoResponse>> getAllStores() {
        return getStoreService.getAllStores();
    }

    @Override
    public BaseApiResponse<StoreInfoResponse> getStoreByAdmin() {
        return getStoreService.getStoreByAdmin();
    }

    @Override
    public BaseApiResponse<StoreInfoResponse> updateStore(Long id, StoreInfoRequest request) {
        return modifyStoreService.updateStore(id, request);
    }

    @Override
    public BaseApiResponse<Void> deleteStore(Long id) {
        return removeStoreService.deleteStore();
    }

    @Override
    public BaseApiResponse<StoreInfoResponse> getStoreByEmployee() {
        return getStoreService.getStoreByEmployee();
    }

    @Override
    public BaseApiResponse<StoreInfoResponse> moderateStore(Long id, String status) {
        return modifyStoreService.moderateStore(id, status);
    }
}
