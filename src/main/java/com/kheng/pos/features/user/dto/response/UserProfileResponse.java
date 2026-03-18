package com.kheng.pos.features.user.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserProfileResponse {
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String roleType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
}
