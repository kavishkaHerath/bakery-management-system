package com.erp.bakery.repository;

import com.erp.bakery.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrderRepository extends JpaRepository<Order, String> {
}
