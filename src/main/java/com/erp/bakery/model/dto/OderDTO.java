package com.erp.bakery.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OderDTO {
    private String orderCode;
    private String supplierName;
    private String requestByUserId;
    private String requestByUserName;
    private LocalDate expectedDate;
    private int numberOfItems;
    private double totalPrice;
    private LocalDate requestDate;
    private String status;

    public OderDTO(String orderCode, String supplierName, String requestByUserId, String requestByUserName,
                   LocalDate expectedDate, int numberOfItems, double totalPrice, LocalDate requestDate, String status) {
        this.orderCode = orderCode;
        this.supplierName = supplierName;
        this.requestByUserId = requestByUserId;
        this.requestByUserName = requestByUserName;
        this.expectedDate = expectedDate;
        this.numberOfItems = numberOfItems;
        this.totalPrice = totalPrice;
        this.requestDate = requestDate;
        this.status = status;
    }
}
