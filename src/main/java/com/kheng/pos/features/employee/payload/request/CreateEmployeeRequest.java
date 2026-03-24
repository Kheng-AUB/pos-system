package com.kheng.pos.features.employee.payload.request;

import com.kheng.pos.databases.pg.userinfo.enums.UserRole;
import lombok.Data;

@Data
public class CreateEmployeeRequest {
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private UserRole role;
    private Long branchId;
}
