package com.kheng.pos.configurations.security.service.impl;


import com.kheng.pos.configurations.jwt.JwtProvider;
import com.kheng.pos.configurations.security.service.contract.AuthService;
import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.enums.UserRole;
import com.kheng.pos.databases.pg.userinfo.mapper.UserInformationMapper;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;

import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.user.dto.UserInformationDto;
import com.kheng.pos.features.auth.dto.request.LoginRequest;
import com.kheng.pos.features.auth.dto.request.SignUpRequest;
import com.kheng.pos.features.auth.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserInformationRepository userInformationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImpl customUserImpl;

    @Override
    public BaseApiResponse<AuthResponse> signup(SignUpRequest request) {
        BaseApiResponse<AuthResponse> response = new BaseApiResponse<>();

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new AppException("Email is required",
                    HttpStatus.BAD_REQUEST, "INVALID_EMAIL");
        }

        UserInformation userInformation = userInformationRepository.findByEmail(request.getEmail());
        if (userInformation != null) {
            throw new AppException("User with this email already registered!",
                    HttpStatus.CONFLICT, "USER_EXISTS");
        }

        if (request.getUserRole() == UserRole.ROLE_ADMIN) {
            throw new AppException("You are not allowed to register as admin!",
                    HttpStatus.FORBIDDEN, "ADMIN_REGISTRATION_DENIED");
        }

        UserInformation newUserInformation = buildUserInformation(request);
        UserInformation savedUserInformation = userInformationRepository.save(newUserInformation);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(newUserInformation.getEmail(), newUserInformation.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwtProvider.generateToken(authentication));
        authResponse.setMessage("User registered successfully!");

        UserInformationDto userInformationDto =
                UserInformationMapper.toDto(savedUserInformation);
        authResponse.setUserInformation(userInformationDto);

        response.setData(authResponse);
        response.isSuccess();

        return response;
    }

    @Override
    public BaseApiResponse<AuthResponse> login(LoginRequest request) {
        BaseApiResponse<AuthResponse> response = new BaseApiResponse<>();

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new AppException("Email is required",
                    HttpStatus.BAD_REQUEST, "INVALID_EMAIL");
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new AppException("Password is required",
                    HttpStatus.BAD_REQUEST, "INVALID_PASSWORD");
        }

        String email = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        // update last login
        UserInformation userInformation = userInformationRepository.findByEmail(email);
        userInformation.setLastLogin(LocalDateTime.now());
        userInformationRepository.save(userInformation);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("User logged in successfully!");
        authResponse.setUserInformation(UserInformationMapper.toDto(userInformation));

        response.setData(authResponse);
        response.isSuccess();
        return response;
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserImpl.loadUserByUsername(email);
        if (userDetails == null) {
            throw new AppException("Invalid email or password",
                    HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new AppException("Invalid email or password",
                    HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS");
        }

        return new UsernamePasswordAuthenticationToken
                (userDetails, null, userDetails.getAuthorities());
    }

    private UserInformation buildUserInformation(SignUpRequest signUpRequest) {
        UserInformation userInformation = new UserInformation();
        userInformation.setEmail(signUpRequest.getEmail());
        userInformation.setFullName(signUpRequest.getFullName());
        userInformation.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userInformation.setPhone(signUpRequest.getPhone());
        userInformation.setUserRole(signUpRequest.getUserRole());
        userInformation.setCreatedAt(LocalDateTime.now());
        userInformation.setUpdatedAt(LocalDateTime.now());
        return userInformation;
    }
}