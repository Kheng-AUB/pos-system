package com.kheng.pos.databases.pg.userinfo.mapper;

import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.features.auth.dto.UserInformationDto;
import jakarta.servlet.http.HttpServletRequest;

public class UserInformationMapper {
    public static UserInformationDto toDto(UserInformation entity, String role) {
        if (entity == null) {
            return null;
        }
        UserInformationDto dto = new UserInformationDto();
        dto.setUserId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setRole(role);
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setLastLogin(entity.getLastLogin());
        return dto;
    }
}
