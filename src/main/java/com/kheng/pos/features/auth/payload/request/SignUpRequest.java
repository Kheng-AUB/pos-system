package com.kheng.pos.features.auth.payload.request;

import com.kheng.pos.databases.user.entities.UserRole;
import lombok.Data;

@Data
public class SignUpRequest {
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private UserRole role;
}
