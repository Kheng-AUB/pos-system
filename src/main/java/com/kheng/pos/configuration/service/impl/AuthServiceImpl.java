package com.kheng.pos.configuration.service.impl;

import com.kheng.pos.configuration.JwtProvider;
import com.kheng.pos.configuration.service.contract.AuthService;
import com.kheng.pos.exception.UserException;
import com.kheng.pos.mapper.UserInfoMapper;
import com.kheng.pos.model.UserInfo;
import com.kheng.pos.payload.dto.UserInfoDto;
import com.kheng.pos.payload.response.AuthResponse;
import com.kheng.pos.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

import static com.kheng.pos.domain.UserRole.ROLE_ADMIN;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImpl customUserImpl;

    @Override
    public AuthResponse signup(UserInfoDto request) throws UserException {
        UserInfo userInfo = userInfoRepository.findByEmail(request.getEmail());
        if (userInfo != null) {
            throw new UserException("User with this email already registered!");
        }

        if (request.getRole().equals(ROLE_ADMIN)) {
            throw new UserException("You are not allowed to register this user!");
        }

        UserInfo newUser = buildUserInfo(request);
        UserInfo savedUser = userInfoRepository.save(newUser);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(newUser.getEmail(), newUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwtProvider.generateToken(authentication));
        authResponse.setMessage("User registered successfully!");
        authResponse.setUserInfo(UserInfoMapper.toDto(savedUser));

        return authResponse;
    }

    @Override
    public AuthResponse login(UserInfoDto request) throws UserException {
        String email = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

//        String role = authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);

        // update last longin
        UserInfo userInfo = userInfoRepository.findByEmail(email);
        userInfo.setLastLogin(LocalDateTime.now());
        userInfoRepository.save(userInfo);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("User logged in successfully!");
        authResponse.setUserInfo(UserInfoMapper.toDto(userInfo));

        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws UserException {
        UserDetails userDetails = customUserImpl.loadUserByUsername(email);
        if (userDetails == null) {
            throw new UserException("Invalid email!");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException("Invalid password!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private UserInfo buildUserInfo(UserInfoDto userInfoDto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(userInfoDto.getEmail());
        userInfo.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        userInfo.setFullName(userInfoDto.getFullName());
        userInfo.setRole(userInfoDto.getRole());
        userInfo.setPhone(userInfoDto.getPhone());
        userInfo.setCreatedAt(LocalDateTime.now());
        userInfo.setUpdatedAt(LocalDateTime.now());
        userInfo.setLastLogin(LocalDateTime.now());
        return userInfo;
    }
}
