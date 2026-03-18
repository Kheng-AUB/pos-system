package com.kheng.pos.features.user.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.databases.pg.userinfo.repository.UserRoleRepository;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.kheng.pos.features.user.service.util.UserInfoServiceUtil.getUserProfileResponseByEmailBaseApiResponse;


@Service
@RequiredArgsConstructor
public class GetUserInfoByEmailService {
    private final UserInformationRepository userInformationRepository;
    private final UserRoleRepository userRoleRepository;

    public BaseApiResponse<UserProfileResponse> getUserInfoByEmail(String email) {
        BaseApiResponse<UserProfileResponse> response = new BaseApiResponse<>();

        return getUserProfileResponseByEmailBaseApiResponse
                (response, email, userInformationRepository, userRoleRepository);
    }
}
