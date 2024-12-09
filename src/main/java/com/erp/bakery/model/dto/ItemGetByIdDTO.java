package com.erp.bakery.model.dto;

import lombok.Data;

@Data
public class ItemGetByIdDTO {
    private Long itemId;
    private String itemName;
    private String displayName;
    private Boolean status;
    private Long categoryId;
    private String categoryName;
    private Long supplierId;
    private String supplierName;
    private String addedEmployee;
    private String modifiedEmployee;
    private String imageUrl;

    public ItemGetByIdDTO(Long itemId, String itemName, String displayName, Boolean status, Long categoryId, String categoryName, Long supplierId,
                          String supplierName, String addedEmployee, String modifiedEmployee, String imageUrl) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.displayName = displayName;
        this.status = status;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.addedEmployee = addedEmployee;
        this.modifiedEmployee = modifiedEmployee;
        this.imageUrl = imageUrl;
    }
}
