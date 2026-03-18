package com.kheng.pos.databases.pg.store.repository;

import com.kheng.pos.databases.pg.store.entity.StoreContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreContactRepository extends JpaRepository<StoreContact, Long> {
}
