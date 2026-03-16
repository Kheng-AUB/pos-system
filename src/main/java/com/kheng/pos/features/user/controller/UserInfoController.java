package com.kheng.pos.features.user.controller;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.exception.UserException;
import com.kheng.pos.features.auth.dto.UserInfoDto;
import com.kheng.pos.features.user.service.contract.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping("/profile")
    public BaseApiResponse<UserInfoDto> getUserProfile(
            @RequestHeader("Authorization") String token) throws UserException {
        return userInfoService.getUserInfoFromJwtToken(token);
    }

    @GetMapping("/{id}")
    public BaseApiResponse<UserInfoDto> getUserById(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long id) throws UserException {
        return userInfoService.getUserInfoById(id);
    }
}
