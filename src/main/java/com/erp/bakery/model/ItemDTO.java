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
    private String addBy;
    private String modifyDate;
    private String modifyBy;

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
        this.addBy = item.getAddBy();
        this.modifyDate = item.getModifyBy() != null ? item.getModifyBy().toString() : item.getModifyBy();
        this.modifyBy = item.getModifyBy();
    }
}
