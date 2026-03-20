package com.kheng.pos.features.branch.payload.request;

import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBranchRequest {
    private String name;

    private String address;

    private String phone;

    private String email;

    private List<String> workingDays;

    private LocalTime openTime;

    private LocalTime closeTime;
}
