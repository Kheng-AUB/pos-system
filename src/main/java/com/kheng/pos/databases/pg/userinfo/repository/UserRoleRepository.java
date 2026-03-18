package com.kheng.pos.databases.pg.userinfo.repository;

import com.kheng.pos.databases.pg.userinfo.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
