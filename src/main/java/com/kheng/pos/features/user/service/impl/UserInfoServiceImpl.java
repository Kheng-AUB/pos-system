package com.kheng.pos.features.user.service.impl;

import com.kheng.pos.configurations.jwt.service.JwtService;
import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.entity.UserRole;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.databases.pg.userinfo.repository.UserRoleRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.user.dto.mapper.UserProfileMapper;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.service.contract.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInformationRepository userInformationRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtService jwtService;

    @Override
    public BaseApiResponse<UserProfileResponse> getUserInfoFromJwtToken(String token) {
        BaseApiResponse<UserProfileResponse> response = new BaseApiResponse<>();

        String email = jwtService.getEmailFromToken(token);
        if (email == null || email.isEmpty()) {
            throw new AppException("Invalid token",
                    HttpStatus.UNAUTHORIZED, "INVALID_TOKEN");
        }

        UserInformation userInfo = userInformationRepository.findByEmail(email);
        if (userInfo == null) {
            throw new AppException("User not found",
                    HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
        }

        UserRole role = userRoleRepository.findById(userInfo.getRoleId()).orElseThrow(
                () -> new AppException("User not found",
                        HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));

        UserProfileResponse userProfileResponse =
                UserProfileMapper.toUserProfileResponse(userInfo, role.getRoleType());

        response.setData(userProfileResponse);
        response.isSuccess();

        return response;
    }

    @Override
    public BaseApiResponse<UserProfileResponse> getCurrentUserInfo() {
        BaseApiResponse<UserProfileResponse> response = new BaseApiResponse<>();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInformation userInfo = userInformationRepository.findByEmail(email);
        if (userInfo == null) {
            throw new AppException("User not found",
                    HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
        }

        UserRole role = userRoleRepository.findById(userInfo.getRoleId()).orElseThrow(
                () -> new AppException("User not found",
                        HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));

        UserProfileResponse userProfileResponse =
                UserProfileMapper.toUserProfileResponse(userInfo, role.getRoleType());
        response.setData(userProfileResponse);
        response.isSuccess();

        return response;
    }

    @Override
    public BaseApiResponse<UserProfileResponse> getUserInfoByEmail(String email) {
        BaseApiResponse<UserProfileResponse> response = new BaseApiResponse<>();

        UserInformation userInfo = userInformationRepository.findByEmail(email);
        if (userInfo == null) {
            throw new AppException("User not found",
                    HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
        }

        UserRole role = userRoleRepository.findById(userInfo.getRoleId()).orElseThrow(
                () -> new AppException("User not found",
                        HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));

        UserProfileResponse userProfileResponse =
                UserProfileMapper.toUserProfileResponse(userInfo, role.getRoleType());
        response.setData(userProfileResponse);
        response.isSuccess();

        return response;
    }

    @Override
    public BaseApiResponse<UserProfileResponse> getUserInfoById(Long id) {
        BaseApiResponse<UserProfileResponse> response = new BaseApiResponse<>();

        Optional<UserInformation> userInfo = userInformationRepository.findById(id);
        if (userInfo.isEmpty()) {
            throw new AppException("User not found",
                    HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
        }

        UserRole role = userRoleRepository.findById(userInfo.get().getRoleId()).orElseThrow(
                () -> new AppException("User not found",
                        HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));

        UserProfileResponse userProfileResponse =
                UserProfileMapper.toUserProfileResponse(userInfo.get(), role.getRoleType());

        response.setData(userProfileResponse);
        response.isSuccess();

        return response;
    }

    @Override
    public BaseApiResponse<List<UserProfileResponse>> getAllUsers() {
        BaseApiResponse<List<UserProfileResponse>> response = new BaseApiResponse<>();

        List<UserInformation> userInfos = userInformationRepository.findAll();
        if (userInfos.isEmpty()) {
            throw new AppException("User not found",
                    HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
        }

        for (UserInformation userInfo : userInfos) {
            UserRole role = userRoleRepository.findById(userInfo.getRoleId()).orElseThrow(
                    () -> new AppException("User not found",
                            HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));
            UserProfileResponse userProfileResponse =
                    UserProfileMapper.toUserProfileResponse(userInfo, role.getRoleType());
            response.getData().add(userProfileResponse);
        }

        response.isSuccess();
        return response;
    }
}
