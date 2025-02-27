package com.erp.bakery.repository;

import com.erp.bakery.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    boolean existsByItem_ItemId(Long itemId);
}
