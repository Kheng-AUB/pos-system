package com.kheng.pos.features.auth.controller;

import com.kheng.pos.configurations.security.service.contract.AuthService;
import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.exception.UserException;
import com.kheng.pos.features.auth.payload.request.LoginRequest;
import com.kheng.pos.features.auth.payload.request.SignUpRequest;
import com.kheng.pos.features.auth.payload.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/ping")   // easy to test
    public String ping() {
        System.err.println(">>> PING ENDPOINT HIT !!!");
        return "pong";
    }

    @PostMapping("/signup")
    public BaseApiResponse<AuthResponse> signup(@RequestBody SignUpRequest request) throws UserException {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public BaseApiResponse<AuthResponse> login(@RequestBody LoginRequest request) throws UserException {
        return authService.login(request);
    }
}
