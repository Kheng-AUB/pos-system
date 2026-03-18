package com.kheng.pos.databases.pg.cateogry.repository;

import com.kheng.pos.databases.pg.cateogry.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByStoreId(Long storeId);
}
