package com.kheng.pos.databases.pg.inventory.repository;

import com.kheng.pos.databases.pg.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    Inventory findByProductIdAndBranchId(Long productId, Long branchId);
    List<Inventory> findByBranchId(Long branchId);
}
