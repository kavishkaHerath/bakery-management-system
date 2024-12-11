package com.erp.bakery.repository;

import com.erp.bakery.model.Order;
import com.erp.bakery.model.dto.OderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    //Get all items details
    @Query("SELECT new com.erp.bakery.model.dto.OderDTO(" +
            "o.orderCode, CONCAT(o.supplier.companeyName, ' - ', o.supplier.supplierName)," +
            "o.numberOfItems, o.totalPrice, o.expectedDate, o.status, " +
            "o.requestBy.userId, CONCAT(o.requestBy.firstName, ' ', o.requestBy.lastName), o.requestDate)" +
            "FROM Order o"
    )
    List<OderDTO> findAllOrdersDetails();

    //Get all items details By manager ID
    @Query("SELECT new com.erp.bakery.model.dto.OderDTO(" +
            "o.orderCode, CONCAT(o.supplier.companeyName, ' - ', o.supplier.supplierName)," +
            "o.numberOfItems, o.totalPrice, o.expectedDate, o.status, " +
            "o.requestBy.userId, CONCAT(o.requestBy.firstName, ' ', o.requestBy.lastName), o.requestDate)" +
            "FROM Order o " +
            "WHERE o.requestBy.userId = :managerId"
    )
    List<OderDTO> findAllOrdersDetailsByManagerID(String managerId);
}
