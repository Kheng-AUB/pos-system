package com.kheng.pos.payload.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kheng.pos.domain.UserRole;
import lombok.*;

import java.time.LocalDateTime;
@Data
public class UserInfoDto {
    private Long id;

    private String fullName;

    private String email;
    private String phone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    private UserRole role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
}
