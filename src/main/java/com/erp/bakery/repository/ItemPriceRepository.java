package com.erp.bakery.repository;

import com.erp.bakery.model.Item;
import com.erp.bakery.model.ItemPrice;
import com.erp.bakery.model.dto.ItemPriceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemPriceRepository extends JpaRepository<ItemPrice, Long> {
}
