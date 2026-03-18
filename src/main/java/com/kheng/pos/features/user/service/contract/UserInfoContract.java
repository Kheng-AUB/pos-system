package com.kheng.pos.features.user.service.contract;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.service.UserInfoService;
import com.kheng.pos.features.user.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoContract implements UserInfoService {
    private final GetCurrentUserInfoService getCurrentUserInfoService;
    private final GetUserInfoByEmailService getUserInfoByEmailService;
    private final GetUserInfoFromTokenService getUserFromTokenService;
    private final GetUserInfoByIdService getUserInfoByIdService;
    private final GetAllUsersInfoService getAllUsersInfoService;

    @Override
    public BaseApiResponse<UserProfileResponse> getUserInfoFromJwtToken(String token) {
        return getUserFromTokenService.getUserInfoFromJwtToken(token);
    }

    @Override
    public BaseApiResponse<UserProfileResponse> getCurrentUserInfo() {
        return getCurrentUserInfoService.getCurrentUserInfo();
    }

    @Override
    public BaseApiResponse<UserProfileResponse> getUserInfoByEmail(String email) {
        return getUserInfoByEmailService.getUserInfoByEmail(email);
    }

    @Override
    public BaseApiResponse<UserProfileResponse> getUserInfoById(Long id) {
        return getUserInfoByIdService.getUserInfoById(id);
    }

    @Override
    public BaseApiResponse<List<UserProfileResponse>> getAllUsers() {
        return getAllUsersInfoService.getAllUsers();
    }
}
