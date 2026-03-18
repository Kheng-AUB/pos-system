package com.kheng.pos.features.auth.dto.response;

import com.kheng.pos.features.user.dto.UserInformationDto;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserInformationDto userInformation;
}
