package com.kheng.pos.features.auth.payload.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
