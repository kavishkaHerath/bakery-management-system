package com.erp.bakery.repository;

import com.erp.bakery.model.Item;
import com.erp.bakery.model.ItemPrice;
import com.erp.bakery.model.dto.ItemPriceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemPriceRepository extends JpaRepository<ItemPrice, Long> {
    boolean existsByItemAndPurchasePriceAndSellingPriceAndStatus(
            Item item,
            double purchasePrice,
            double sellingPrice,
            boolean status
    );

    //Get all GRN details
    @Query("SELECT new com.erp.bakery.model.dto.ItemPriceDTO(" +
            "i.id, i.item.itemName, i.item.displayName, CONCAT(i.item.supplier.companeyName, ' - ', i.item.supplier.supplierName)," +
            "i.purchasePrice, i.sellingPrice, i.status, i.addedDate)" +
            "FROM ItemPrice i "
    )
    List<ItemPriceDTO> getAllItemsWithPrice();

}
