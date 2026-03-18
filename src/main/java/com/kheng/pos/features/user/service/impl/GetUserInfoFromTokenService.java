package com.kheng.pos.features.user.service.impl;

import com.kheng.pos.configurations.jwt.service.JwtService;
import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.databases.pg.userinfo.repository.UserRoleRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.kheng.pos.features.user.service.util.UserInfoServiceUtil.getUserProfileResponseByEmailBaseApiResponse;

@Service
@RequiredArgsConstructor
public class GetUserInfoFromTokenService {
    private final UserInformationRepository userInformationRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtService jwtService;

    public BaseApiResponse<UserProfileResponse> getUserInfoFromJwtToken(String token) {
        BaseApiResponse<UserProfileResponse> response = new BaseApiResponse<>();

        String email = jwtService.getEmailFromToken(token);
        if (email == null || email.isEmpty()) {
            throw new AppException("Invalid token",
                    HttpStatus.UNAUTHORIZED, "INVALID_TOKEN");
        }

        return getUserProfileResponseByEmailBaseApiResponse
                (response, email, userInformationRepository, userRoleRepository);
    }
}
