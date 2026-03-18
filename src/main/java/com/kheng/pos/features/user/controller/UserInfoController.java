package com.kheng.pos.features.user.controller;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping("/profile")
    public BaseApiResponse<UserProfileResponse> getUserProfile(
            @RequestHeader("Authorization") String token) {
        return userInfoService.getUserInfoFromJwtToken(token);
    }

    @GetMapping("/{id}")
    public BaseApiResponse<UserProfileResponse> getUserById(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long id) {
        return userInfoService.getUserInfoById(id);
    }
}
