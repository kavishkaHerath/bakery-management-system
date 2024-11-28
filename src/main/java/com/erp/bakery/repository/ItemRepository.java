package com.erp.bakery.repository;

import com.erp.bakery.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByItemName(String itemName);

    boolean existsByCategory_CategoryId(Long categoryId);

    boolean existsBySupplier_SupplierCode(Long supplierCode);
}