package com.kheng.pos.features.user.service.util;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.entity.UserRole;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.databases.pg.userinfo.repository.UserRoleRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.mapper.UserProfileMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserInfoServiceUtil {
    public static BaseApiResponse<UserProfileResponse> getUserProfileResponseByEmailBaseApiResponse(
            BaseApiResponse<UserProfileResponse> response,
            String email,
            UserInformationRepository userInformationRepository,
            UserRoleRepository userRoleRepository) {
        UserInformation userInfo = userInformationRepository.findByEmail(email);
        if (userInfo == null) {
            throw new AppException("User not found",
                    HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
        }

        UserRole role = userRoleRepository.findById(userInfo.getRoleId()).orElseThrow(
                () -> new AppException("User not found",
                        HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));

        UserProfileResponse userProfileResponse =
                UserProfileMapper.toUserProfileResponse(userInfo, role.getRoleType());
        response.setData(userProfileResponse);
        response.isSuccess();

        return response;
    }
}
