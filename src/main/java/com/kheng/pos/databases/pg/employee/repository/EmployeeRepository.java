package com.kheng.pos.databases.pg.employee.repository;

import com.kheng.pos.databases.pg.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByBranchId(Long branchId);

    @Query(value = """
                SELECT e.*
                FROM tbl_employee e
                JOIN tbl_user_info u ON e.user_id = u.id
                WHERE e.store_id = :storeId
                AND u.user_role IN (:roleTypes)
            """, nativeQuery = true)
    List<Employee> findByStoreIdAndRole(Long storeId, List<String> roleTypes);
}
