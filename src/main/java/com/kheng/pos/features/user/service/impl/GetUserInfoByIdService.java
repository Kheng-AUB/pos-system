package com.kheng.pos.features.user.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.mapper.UserProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetUserInfoByIdService {
    private final UserInformationRepository userInformationRepository;

    public BaseApiResponse<UserProfileResponse> getUserInfoById(Long id) {
        BaseApiResponse<UserProfileResponse> response = new BaseApiResponse<>();

        Optional<UserInformation> userInfo = userInformationRepository.findById(id);
        if (userInfo.isEmpty()) {
            throw new AppException("User not found",
                    HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
        }

        UserProfileResponse userProfileResponse =
                UserProfileMapper.toUserProfileResponse(userInfo.get());

        response.setData(userProfileResponse);
        response.isSuccess();

        return response;
    }
}
