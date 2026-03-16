package com.kheng.pos.features.user.service.impl;

import com.kheng.pos.configurations.jwt.JwtProvider;
import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.user.entities.UserInfo;
import com.kheng.pos.databases.user.repositories.UserInfoRepository;
import com.kheng.pos.exception.UserException;
import com.kheng.pos.features.auth.dto.UserInfoDto;
import com.kheng.pos.features.auth.mapper.UserInfoMapper;
import com.kheng.pos.features.user.service.contract.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final JwtProvider jwtProvider;

    @Override
    public BaseApiResponse<UserInfoDto> getUserInfoFromJwtToken(String token) throws UserException {

        BaseApiResponse<UserInfoDto> response = new BaseApiResponse<>();

        String email = jwtProvider.getEmailFromToken(token);
        if (email == null) {
            throw new UserException("Invalid token");
        }

        UserInfo userInfo = userInfoRepository.findByEmail(email);
        if (userInfo == null) {
            throw new UserException("User not found");
        }

        response.setData(UserInfoMapper.toDto(userInfo));
        response.isSuccess();

        return response;
    }

    @Override
    public UserInfoDto getCurrentUserInfo() throws UserException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInfo userInfo = userInfoRepository.findByEmail(email);
        if (userInfo == null) {
            throw new UserException("User not found");
        }
        return UserInfoMapper.toDto(userInfo);
    }

    @Override
    public UserInfoDto getUserInfoByEmail(String email) throws UserException {
        UserInfo userInfo = userInfoRepository.findByEmail(email);
        if (userInfo == null) {
            throw new UserException("User not found");
        }
        return UserInfoMapper.toDto(userInfo);
    }

    @Override
    public BaseApiResponse<UserInfoDto> getUserInfoById(Long id) throws UserException {
        BaseApiResponse<UserInfoDto> response = new BaseApiResponse<>();

        Optional<UserInfo> userInfo = userInfoRepository.findById(id);
        if (userInfo.isEmpty()) {
            throw new UserException("User not found");
        }

        response.setData(UserInfoMapper.toDto(userInfo.get()));
        response.isSuccess();

        return response;
    }

    @Override
    public List<UserInfoDto> getAllUsers() {
        return userInfoRepository.findAll().stream()
                .map(UserInfoMapper::toDto).toList();
    }
}
