package com.erp.bakery.model.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class OderDTO {
    private String orderCode;
    private String supplierName;
    private int numberOfItems;
    private double totalPrice;
    private LocalDate expectedDate;
    private String status;
    private String requestByUserId;
    private String requestByUserName;
    private LocalDate requestDate;
    private LocalDate approvedDate;

    public OderDTO(String orderCode, String supplierName, int numberOfItems, double totalPrice, LocalDate expectedDate,
                   String status, String requestByUserId, String requestByUserName, LocalDate requestDate, LocalDate approvedDate) {
        this.orderCode = orderCode;
        this.supplierName = supplierName;
        this.numberOfItems = numberOfItems;
        this.totalPrice = totalPrice;
        this.expectedDate = expectedDate;
        this.status = status;
        this.requestByUserId = requestByUserId;
        this.requestByUserName = requestByUserName;
        this.requestDate = requestDate;
        this.approvedDate = approvedDate;
    }
}
