package com.kheng.pos.features.user.mapper;

import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;

public class UserProfileMapper {
    public static UserProfileResponse toUserProfileResponse(UserInformation userInformation) {
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setUserId(userInformation.getId());
        userProfileResponse.setFullName(userInformation.getFullName());
        userProfileResponse.setEmail(userInformation.getEmail());
        userProfileResponse.setPhone(userInformation.getPhone());
        userProfileResponse.setRole(userInformation.getUserRole().name());
        userProfileResponse.setCreatedAt(userInformation.getCreatedAt());
        userProfileResponse.setUpdatedAt(userInformation.getUpdatedAt());
        userProfileResponse.setLastLogin(userInformation.getLastLogin());
        return userProfileResponse;
    }
}
