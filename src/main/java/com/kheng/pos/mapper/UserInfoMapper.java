package com.kheng.pos.mapper;

import com.kheng.pos.model.UserInfo;
import com.kheng.pos.payload.dto.UserInfoDto;

public class UserInfoMapper {
    public static UserInfoDto toDto(UserInfo userInfo) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(userInfo.getId());
        userInfoDto.setEmail(userInfo.getEmail());
        userInfoDto.setPhone(userInfo.getPhone());
        userInfoDto.setFullName(userInfo.getFullName());
        userInfoDto.setRole(userInfo.getRole());

        userInfoDto.setCreatedAt(userInfo.getCreatedAt());
        userInfoDto.setUpdatedAt(userInfo.getUpdatedAt());
        userInfoDto.setLastLogin(userInfo.getLastLogin());
        return userInfoDto;
    }
}
