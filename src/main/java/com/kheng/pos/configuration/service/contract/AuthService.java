package com.kheng.pos.configuration.service.contract;

import com.kheng.pos.exception.UserException;
import com.kheng.pos.payload.dto.UserInfoDto;
import com.kheng.pos.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(UserInfoDto userInfo) throws UserException;
    AuthResponse login(UserInfoDto userInfo) throws UserException;
}
