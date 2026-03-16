package com.kheng.pos.payload.response;

import com.kheng.pos.payload.dto.UserInfoDto;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserInfoDto userInfo;
}
