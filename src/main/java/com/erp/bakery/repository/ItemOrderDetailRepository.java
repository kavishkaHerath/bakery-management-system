package com.erp.bakery.repository;

import com.erp.bakery.model.ItemOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrderDetailRepository extends JpaRepository<ItemOrderDetail, Long> {
}
