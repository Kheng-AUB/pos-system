package com.kheng.pos.features.user.service.contract;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.exception.UserException;
import com.kheng.pos.features.auth.dto.UserInfoDto;

import java.util.List;

public interface UserInfoService {
    BaseApiResponse<UserInfoDto> getUserInfoFromJwtToken(String token) throws UserException;
    UserInfoDto getCurrentUserInfo() throws UserException;
    UserInfoDto getUserInfoByEmail(String email) throws UserException;
    BaseApiResponse<UserInfoDto> getUserInfoById(Long id) throws UserException;
    List<UserInfoDto> getAllUsers();
}
