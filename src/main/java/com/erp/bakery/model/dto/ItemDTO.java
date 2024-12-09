package com.erp.bakery.model.dto;

import lombok.Data;


@Data
public class ItemDTO {
    private Long itemId;
    private String itemName;
    private String displayName;
    private Boolean status;
    private String categoryName;
    private String supplierName;
    private String addedEmployee;
    private String modifiedEmployee;
    private String imageUrl;

    public ItemDTO(Long itemId, String itemName, String displayName, Boolean status, String categoryName,
                   String supplierName, String addedEmployee, String modifiedEmployee, String imageUrl) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.displayName = displayName;
        this.status = status;
        this.categoryName = categoryName;
        this.supplierName = supplierName;
        this.addedEmployee = addedEmployee;
        this.modifiedEmployee = modifiedEmployee;
        this.imageUrl = imageUrl;
    }
}
