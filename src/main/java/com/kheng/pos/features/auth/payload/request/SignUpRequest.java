package com.kheng.pos.features.auth.payload.request;

import lombok.Data;

@Data
public class SignUpRequest {
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private Long roleId;
}
