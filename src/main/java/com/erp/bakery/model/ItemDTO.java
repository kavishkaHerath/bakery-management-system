package com.erp.bakery.model;

import lombok.Data;


@Data
public class ItemDTO {
    private Long itemId;
    private String itemName;
    private String displayName;
    private Boolean status;
    private Long categoryId;
    private String categoryName;
    private Long supplierCode;
    private String supplierName;
    private String addDate;
    private Employee addedEmployee;
    private String modifyDate;
    private Employee modifiedEmployee;

    public ItemDTO(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.displayName = item.getDisplayName();
        this.status = item.getStatus();
        this.categoryId = item.getCategory().getCategoryId();
        this.categoryName = item.getCategory().getCategoryName();
        this.supplierCode = item.getSupplier().getSupplierCode();
        this.supplierName = item.getSupplier().getSupplierName();
        this.addDate = item.getAddDate().toString();
        this.addedEmployee = item.getAddedEmployee();
        this.modifyDate = item.getModifiedEmployee() == null ? "" : item.getModifyDate().toString();
        this.modifiedEmployee = item.getModifiedEmployee();
    }
}
