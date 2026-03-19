package com.kheng.pos.databases.pg.branch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @Column(name = "working_days")
    private List<String> workingDays;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "manager_id")
    private Long managerId;

    @Column(name = "open_time")
    private LocalDateTime openTime;
    @Column(name = "close_time")
    private LocalDateTime closeTime;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "updated_at")
    private LocalDateTime updateAt;
}
