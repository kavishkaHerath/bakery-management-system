package com.erp.bakery.repository;

import com.erp.bakery.model.ItemsOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrderRepository extends JpaRepository<ItemsOrder, String> {
}
