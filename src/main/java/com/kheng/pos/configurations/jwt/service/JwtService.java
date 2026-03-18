package com.kheng.pos.configurations.jwt.service;

import com.kheng.pos.configurations.jwt.constant.JwtConstant;
import com.kheng.pos.exception.AppException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

    public String generateToken(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = populateAuthorities(authorities);

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + JwtConstant.JWT_EXPIRATION_TIME)) // 24 hours
                .claim("email", authentication.getName())
                .claim("authorities", roles)
                .signWith(secretKey)
                .compact();
    }

    public String getEmailFromToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new AppException("Token is required", HttpStatus.BAD_REQUEST, "INVALID_TOKEN");
        }

        try {
            // Remove "Bearer " prefix if present
            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

            if (jwtToken.isEmpty()) {
                throw new AppException("Invalid token format", HttpStatus.BAD_REQUEST, "INVALID_TOKEN");
            }

            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload();

            Object emailObj = claims.get("email");
            if (emailObj == null) {
                throw new AppException("Email not found in token", HttpStatus.BAD_REQUEST, "INVALID_TOKEN");
            }

            return emailObj.toString();
        } catch (JwtException ex) {
            throw new AppException("Invalid or expired token", HttpStatus.UNAUTHORIZED, "INVALID_TOKEN");
        }
    }

    public String getAuthoritiesFromToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new AppException("Token is required", HttpStatus.BAD_REQUEST, "INVALID_TOKEN");
        }

        try {
            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload();

            Object authObj = claims.get("authorities");
            return authObj != null ? authObj.toString() : "";
        } catch (JwtException ex) {
            throw new AppException("Invalid or expired token", HttpStatus.UNAUTHORIZED, "INVALID_TOKEN");
        }
    }

    public Claims getAllClaimsFromToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new AppException("Token is required", HttpStatus.BAD_REQUEST, "INVALID_TOKEN");
        }

        try {
            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload();
        } catch (JwtException ex) {
            throw new AppException("Invalid or expired token", HttpStatus.UNAUTHORIZED, "INVALID_TOKEN");
        }
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority grantedAuthority : authorities) {
            authoritiesSet.add(grantedAuthority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}

