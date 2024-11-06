package com.erp.bakery.repository;

import com.erp.bakery.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.roleId = ?1")
    long countByRoleId(String roleId);
}
