package com.erp.bakery.model.dto;

import com.erp.bakery.model.Employee;
import com.erp.bakery.model.OrderDetail;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderModify {
    private String orderCode;
    private Employee requestBy;
    private LocalDate expectedDate;
    private int numberOfItems;
    private double totalPrice;
    private LocalDate requestDate;
    private String modifyUserID;
    private LocalDate approvedDate;
    private List<OrderDetail> orderDetails;

    public OrderModify(String orderCode, Employee requestBy, LocalDate expectedDate, int numberOfItems, double totalPrice, LocalDate requestDate, String modifyUserID, LocalDate approvedDate, List<OrderDetail> orderDetails) {
        this.orderCode = orderCode;
        this.requestBy = requestBy;
        this.expectedDate = expectedDate;
        this.numberOfItems = numberOfItems;
        this.totalPrice = totalPrice;
        this.requestDate = requestDate;
        this.modifyUserID = modifyUserID;
        this.approvedDate = approvedDate;
        this.orderDetails = orderDetails;
    }
}
