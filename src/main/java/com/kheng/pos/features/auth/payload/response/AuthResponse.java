package com.kheng.pos.features.auth.payload.response;

import com.kheng.pos.features.auth.dto.UserInformationDto;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserInformationDto userInformation;
}
