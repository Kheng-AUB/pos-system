package com.kheng.pos.databases.pg.userinfo.repository;

import com.kheng.pos.databases.pg.userinfo.entity.UserStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStoreRepository extends JpaRepository<UserStore, Long> {
    UserStore findTopByUserIdOrderByIdDesc(Long userId);
    UserStore findByStoreId(Long storeId);
}
