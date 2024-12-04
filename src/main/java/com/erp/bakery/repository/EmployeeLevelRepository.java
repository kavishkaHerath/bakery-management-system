package com.erp.bakery.repository;

import com.erp.bakery.model.EmployeeLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeLevelRepository extends JpaRepository<EmployeeLevel, Long> {
    boolean existsByLevelName(String levelName);
}
