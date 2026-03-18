package com.kheng.pos.databases.pg.userinfo.repository;

import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInformationRepository extends JpaRepository<UserInformation, Long> {
    UserInformation findByEmail(String email);
}
