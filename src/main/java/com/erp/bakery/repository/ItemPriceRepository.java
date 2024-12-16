package com.erp.bakery.repository;

import com.erp.bakery.model.ItemPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPriceRepository extends JpaRepository<ItemPrice, Long> {
//    boolean existsByItemId(Long itemId);
//    boolean existsByItem(Long itemId);
}
