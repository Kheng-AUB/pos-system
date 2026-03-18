package com.kheng.pos.features.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInformationDto {
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
}
