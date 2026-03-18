package com.kheng.pos.databases.pg.store.repository;

import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreInfoRepository extends JpaRepository<StoreInfo, Long> {
    StoreInfo findTopByIdOrderByIdDesc(Long storeId);
}
