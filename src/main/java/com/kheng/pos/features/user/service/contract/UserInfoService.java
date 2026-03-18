package com.kheng.pos.features.user.service.contract;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;

import java.util.List;

public interface UserInfoService {
    BaseApiResponse<UserProfileResponse> getUserInfoFromJwtToken(String token);
    BaseApiResponse<UserProfileResponse> getCurrentUserInfo();
    BaseApiResponse<UserProfileResponse> getUserInfoByEmail(String email);
    BaseApiResponse<UserProfileResponse> getUserInfoById(Long id);
    BaseApiResponse<List<UserProfileResponse>> getAllUsers();
}


