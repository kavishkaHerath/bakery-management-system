package com.erp.bakery.repository;

import com.erp.bakery.model.Supplier;
import com.erp.bakery.model.SupplierDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    // Custom query to find all supplier with status true
    @Query("SELECT new com.erp.bakery.model.SupplierDTO(s.supplierCode, CONCAT(s.companeyName, ' - ', s.supplierName)) FROM Supplier s WHERE s.status = true")
    List<SupplierDTO> findActiveSupplier();

}
