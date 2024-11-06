package com.erp.bakery.repository;

import com.erp.bakery.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
//    boolean existsByEmail(String email);
//    boolean existsByPhone(String phone);
}
