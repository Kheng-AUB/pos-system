package com.kheng.pos.features.auth.controller;

import com.kheng.pos.configurations.security.service.contract.AuthService;
import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.auth.dto.request.LoginRequest;
import com.kheng.pos.features.auth.dto.request.SignUpRequest;
import com.kheng.pos.features.auth.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/ping")   // easy to test
    public String ping() {
        return "service is running";
    }

    @PostMapping("/signup")
    public BaseApiResponse<AuthResponse> signup(@RequestBody SignUpRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public BaseApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
