package com.kheng.pos.features.branch.payload.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateBranchRequest {

    private String name;

    private String address;

    private String phone;

    private String email;

    private List<String> workingDays;

    private Long storeId;

    private Long managerId;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;
}
