package com.erp.bakery.repository;

import com.erp.bakery.model.Item;
import com.erp.bakery.model.ItemActiveDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByItemName(String itemName);
    boolean existsByCategory_CategoryId(Long categoryId);
    boolean existsBySupplier_SupplierCode(Long supplierCode);

    @Query("SELECT new com.erp.bakery.model.ItemActiveDTO(i.itemId, CONCAT(i.itemName, ' - ', i.displayName))" +
            "FROM Item i " +
            "WHERE i.status = true AND i.supplier.supplierCode = :supplierCode ")
    List<ItemActiveDTO> findActiveItemsBySupplier(Long supplierCode);
}