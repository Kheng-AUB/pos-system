package com.kheng.pos.features.auth.payload.response;

import com.kheng.pos.features.auth.dto.UserInfoDto;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserInfoDto userInfo;
}
