package com.kheng.pos.databases.pg.branch.repository;

import com.kheng.pos.databases.pg.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findByStoreId(Long storeId);
}
