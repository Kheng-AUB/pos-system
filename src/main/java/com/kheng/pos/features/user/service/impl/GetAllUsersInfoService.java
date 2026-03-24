package com.kheng.pos.features.user.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.user.mapper.UserProfileMapper;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllUsersInfoService {
    private final UserInformationRepository userInformationRepository;

    public BaseApiResponse<List<UserProfileResponse>> getAllUsers() {
        BaseApiResponse<List<UserProfileResponse>> response = new BaseApiResponse<>();

        List<UserInformation> userInfos = userInformationRepository.findAll();
        if (userInfos.isEmpty()) {
            throw new AppException("User not found",
                    HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
        }

        response.setData(userInfos.stream()
                .map(UserProfileMapper::toUserProfileResponse)
                .toList());
        response.isSuccess();
        return response;
    }
}
