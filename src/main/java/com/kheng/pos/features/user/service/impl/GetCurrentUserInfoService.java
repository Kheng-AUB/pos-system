package com.kheng.pos.features.user.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.kheng.pos.features.user.service.util.UserInfoServiceUtil.getUserProfileResponseByEmailBaseApiResponse;

@Service
@RequiredArgsConstructor
public class GetCurrentUserInfoService {
    private final UserInformationRepository userInformationRepository;

    public BaseApiResponse<UserProfileResponse> getCurrentUserInfo() {
        BaseApiResponse<UserProfileResponse> response = new BaseApiResponse<>();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserProfileResponseByEmailBaseApiResponse
                (response, email, userInformationRepository);
    }
}
