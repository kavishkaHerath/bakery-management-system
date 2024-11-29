package com.erp.bakery.repository;

import com.erp.bakery.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
