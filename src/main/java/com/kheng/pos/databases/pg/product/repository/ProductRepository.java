package com.kheng.pos.databases.pg.product.repository;

import com.kheng.pos.databases.pg.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStoreId(Long storeId);

    @Query(value = """
            SELECT p FROM tbl_product p
            WHERE p.store_id = :storeId 
            AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
            OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword,'%'))
            OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :keyword, '%'))                    
            """, nativeQuery = true)
    List<Product> searchByKeyword(@Param("storeId") Long storeId, @Param("keyword") String keyword);
}
