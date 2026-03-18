package com.kheng.pos.databases.pg.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tbl_store_contact")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "address",insertable=false, updatable=false)
    private String address;

    @Column(name = "phone",insertable=false, updatable=false)
    private String phone;

    @Column(name = "email",insertable=false, updatable=false)
    private String email;
}
