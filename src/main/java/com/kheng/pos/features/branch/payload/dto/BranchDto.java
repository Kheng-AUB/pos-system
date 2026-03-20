package com.kheng.pos.features.branch.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class BranchDto {
    private Long branchId;

    private String name;

    private String address;

    private String phone;

    private String email;

    private List<String> workingDays;

    private Long storeId;

    private Long managerId;

    private LocalTime openTime;

    private LocalTime closeTime;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
