package com.kheng.pos.features.employee.payload.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeResponse {
    private Long employeeId;
    private String fullName;
    private String email;
    private String phone;
    private String role;
    private Long storeId;
    private Long branchId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
}
