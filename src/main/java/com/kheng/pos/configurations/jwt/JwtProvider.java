package com.kheng.pos.configurations.jwt;

import com.kheng.pos.configurations.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtService jwtService;

    public String generateToken(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }

    public String getEmailFromToken(String token) {
        return jwtService.getEmailFromToken(token);
    }
}
