package com.kheng.pos.databases.pg.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Table(name = "tbl_inventory")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    Long inventoryId;

    @Column(name = "branch_id")
    Long branchId;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "quantity", nullable = false)
    Integer quantity;

    @Column(name = "last_update")
    LocalDateTime lastUpdate;
}
