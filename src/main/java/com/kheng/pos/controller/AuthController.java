package com.kheng.pos.controller;

import com.kheng.pos.configuration.service.contract.AuthService;
import com.kheng.pos.exception.UserException;
import com.kheng.pos.payload.dto.UserInfoDto;
import com.kheng.pos.payload.response.AuthResponse;
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
    public ResponseEntity<AuthResponse> signup(@RequestBody UserInfoDto request) throws UserException {
        System.err.println(">>> SIGNUP CALLED with email: " + request.getEmail());
        System.out.println("signup");
        return new ResponseEntity<>(authService.signup(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserInfoDto request) throws UserException {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }
}
