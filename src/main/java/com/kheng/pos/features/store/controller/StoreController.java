package com.kheng.pos.features.store.controller;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.store.dto.request.StoreInfoRequest;
import com.kheng.pos.features.store.dto.response.StoreInfoResponse;
import com.kheng.pos.features.store.service.contract.StoreService;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.service.contract.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final UserInfoService userInfoService;

    @GetMapping
    public BaseApiResponse<List<StoreInfoResponse>> getAllStores(
            @RequestHeader("Authorization") String token
    ) {
        return storeService.getAllStores();
    }

    @PostMapping
    public BaseApiResponse<StoreInfoResponse> createStore(
            @RequestHeader("Authorization") String token,
            @RequestBody StoreInfoRequest request) {
        
        BaseApiResponse<UserProfileResponse> userResponse = userInfoService.getUserInfoFromJwtToken(token);
        UserProfileResponse userInfo = userResponse.getData();
        
        if (userInfo == null) {
            throw new AppException("User information not found in token", HttpStatus.UNAUTHORIZED, "INVALID_USER");
        }

        return storeService.createStore(request, userInfo);
    }

    @GetMapping("/admin")
    public BaseApiResponse<StoreInfoResponse> getStoreByAdmin(
            @RequestHeader("Authorization") String token
    ) {
        return storeService.getStoreByAdmin();
    }

    @GetMapping("/employee")
    public BaseApiResponse<StoreInfoResponse> getStoreByEmployee(
            @RequestHeader("Authorization") String token
    ) {
        return storeService.getStoreByEmployee();
    }

    @GetMapping("/{id}")
    public BaseApiResponse<StoreInfoResponse> getStoreById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        return storeService.getStoreById(id);
    }

    @PutMapping("/{id}")
    public BaseApiResponse<StoreInfoResponse> updateStore(
            @PathVariable Long id,
            @RequestBody StoreInfoRequest request) {
        return storeService.updateStore(id, request);
    }

    @DeleteMapping("/{id}")
    public BaseApiResponse<Void> deleteStore(@PathVariable Long id) {
        return storeService.deleteStore(id);
    }

    @PutMapping("/{id}/moderate")
    public BaseApiResponse<StoreInfoResponse> moderateStore(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return storeService.moderateStore(id, status);
    }
}
