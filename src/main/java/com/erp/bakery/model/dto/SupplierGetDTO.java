package com.erp.bakery.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SupplierGetDTO {
    private Long supplierCode;
    private String companeyName;
    private String companeyRegisterNo;
    private String supplierName;
    private String email;
    private String phone;
    private String address;
    private Boolean status;
    private String addedEmployeeId;
    private LocalDate addDate;
    private String imageUrl;

    public SupplierGetDTO(Long supplierCode, String companeyName, String companeyRegisterNo, String supplierName, String email, String phone, String address, Boolean status, String addedEmployeeId, LocalDate addDate, String imageUrl) {
        this.supplierCode = supplierCode;
        this.companeyName = companeyName;
        this.companeyRegisterNo = companeyRegisterNo;
        this.supplierName = supplierName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.addedEmployeeId = addedEmployeeId;
        this.addDate = addDate;
        this.imageUrl = imageUrl;
    }
}
