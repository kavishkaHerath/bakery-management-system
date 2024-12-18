package com.erp.bakery.repository;

import com.erp.bakery.model.Item;
import com.erp.bakery.model.ItemActiveDTO;
import com.erp.bakery.model.dto.ItemDTO;
import com.erp.bakery.model.dto.ItemGetByIdDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByItemName(String itemName);
    boolean existsByCategory_CategoryId(Long categoryId);
    boolean existsBySupplier_SupplierCode(Long supplierCode);

    //Get all items details
    @Query("SELECT new com.erp.bakery.model.dto.ItemDTO(" +
            "i.itemId, i.itemName, i.displayName, i.status, i.category.categoryName," +
            "CONCAT(i.supplier.companeyName, ' - ', i.supplier.supplierName)," +
            "i.addedEmployee.userId, i.modifiedEmployee.userId, i.imageUrl)" +
            "FROM Item i"
    )
    List<ItemDTO> findAllItemsDetails();

    //Get item details by Item Id
    @Query("SELECT new com.erp.bakery.model.dto.ItemGetByIdDTO(" +
            "i.itemId, i.itemName, i.displayName, i.status, i.category.categoryId, i.category.categoryName," +
            "i.supplier.supplierCode, CONCAT(i.supplier.companeyName, ' - ', i.supplier.supplierName)," +
            "i.addedEmployee.userId, i.modifiedEmployee.userId, i.imageUrl)" +
            "FROM Item i " +
            "WHERE i.itemId = :itemId"
    )
    Optional<ItemGetByIdDTO> findItemDetailsById(@Param("itemId") Long itemId);

    @Query("SELECT new com.erp.bakery.model.ItemActiveDTO(i.itemId, CONCAT(i.itemName, ' - ', i.displayName), " +
            "COALESCE(p.purchasePrice, 0)) " +
            "FROM Item i LEFT JOIN ItemPrice p ON i.itemId = p.item.itemId " +
            "WHERE i.status = true AND i.supplier.supplierCode = :supplierCode "
    )
    List<ItemActiveDTO> findActiveItemsBySupplier(Long supplierCode);
}