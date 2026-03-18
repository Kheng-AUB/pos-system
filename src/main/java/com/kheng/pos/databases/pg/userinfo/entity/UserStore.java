package com.kheng.pos.databases.pg.userinfo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "tbl_user_store")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
