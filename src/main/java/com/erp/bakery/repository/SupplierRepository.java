package com.erp.bakery.repository;


import com.erp.bakery.model.ItemActiveDTO;
import com.erp.bakery.model.Supplier;
import com.erp.bakery.model.SupplierDTO;
import com.erp.bakery.model.dto.SupplierGetDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    //Get all supplier details
    @Query("SELECT new com.erp.bakery.model.dto.SupplierGetDTO(s.supplierCode, s.companeyName, s.companeyRegisterNo," +
            " s.supplierName, s.email, s.phone, s.address, s.status, s.addedEmployee.userId, s.addDate, s.imageUrl)" +
            "FROM Supplier s"
    )
    List<SupplierGetDTO> findAllSupplierDetails();

    //Get Supplier By Supplier Id
    @Query("SELECT new com.erp.bakery.model.dto.SupplierGetDTO(s.supplierCode, s.companeyName, s.companeyRegisterNo," +
            " s.supplierName, s.email, s.phone, s.address, s.status, s.addedEmployee.userId, s.addDate, s.imageUrl)" +
            "FROM Supplier s " +
            "WHERE s.supplierCode = :supplierCode"
    )
    Optional<SupplierGetDTO> findSupplierDetailsBySupplier(@Param("supplierCode") Long supplierCode);
    
    // Custom query to find all supplier with status true
    @Query("SELECT new com.erp.bakery.model.SupplierDTO(s.supplierCode, CONCAT(s.companeyName, ' - ', s.supplierName)) " +
            "FROM Supplier s " +
            "WHERE s.status = true"
    )
    List<SupplierDTO> findActiveSupplier();

}
