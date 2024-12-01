package com.erp.bakery.repository;


import com.erp.bakery.model.Supplier;
import com.erp.bakery.model.SupplierDTO;
import com.erp.bakery.model.dto.SupplierGetDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    //Get all supplier details
    @Query("SELECT new com.erp.bakery.model.dto.SupplierGetDTO(s.supplierCode, s.companeyName, s.companeyRegisterNo," +
            " s.supplierName, s.email, s.phone, s.address, s.status, e.userId, s.addDate, s.imageUrl)" +
            "FROM Supplier s JOIN s.addedEmployee e"
    )
    List<SupplierGetDTO> findAllSupplierDetails();

//    //Get Supplier details By Supplier ID
//    @Query("SELECT new com.erp.bakery.model.dto.SupplierGetDTO(s.supplierCode, s.companeyName, s.companeyRegisterNo," +
//            " s.supplierName, s.email, s.phone, s.address, s.status, e.userId, s.addDate, s.imageUrl)" +
//            "FROM Supplier s JOIN s.addedEmployee e" +
//            "WHERE s.supplierCode = :supplierCode"
//    )
//    SupplierGetDTO findSupplierDetailsBySupplier(Long supplierCode);

    // Custom query to find all supplier with status true
    @Query("SELECT new com.erp.bakery.model.SupplierDTO(s.supplierCode, CONCAT(s.companeyName, ' - ', s.supplierName)) " +
            "FROM Supplier s " +
            "WHERE s.status = true"
    )
    List<SupplierDTO> findActiveSupplier();

}
