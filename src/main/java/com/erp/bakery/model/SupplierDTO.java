package com.erp.bakery.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupplierDTO {
    private Long supplierCode;
    private String supplierName;

    public SupplierDTO(Long supplierCode, String supplierName) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
    }
}
