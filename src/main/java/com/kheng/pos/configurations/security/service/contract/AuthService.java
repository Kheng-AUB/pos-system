package com.kheng.pos.configurations.security.service.contract;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.auth.payload.request.LoginRequest;
import com.kheng.pos.features.auth.payload.request.SignUpRequest;
import com.kheng.pos.features.auth.payload.response.AuthResponse;

public interface AuthService {
    BaseApiResponse<AuthResponse> signup(SignUpRequest request);
    BaseApiResponse<AuthResponse> login(LoginRequest request);
}
