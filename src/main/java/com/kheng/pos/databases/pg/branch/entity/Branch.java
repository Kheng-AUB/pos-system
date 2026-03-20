package com.kheng.pos.databases.pg.branch.entity;

import com.kheng.pos.databases.pg.branch.enums.DaysOfWeek;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Table(name = "tbl_branch")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "tbl_branch_working_days",
            joinColumns = @JoinColumn(name = "branch_id")
    )
    @Column(name = "day_name")
    private List<DaysOfWeek> workingDays;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "manager_id")
    private Long managerId;

    @Column(name = "open_time")
    private LocalTime openTime;
    @Column(name = "close_time")
    private LocalTime closeTime;

    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "updated_at")
    private LocalDateTime updateAt;
}
