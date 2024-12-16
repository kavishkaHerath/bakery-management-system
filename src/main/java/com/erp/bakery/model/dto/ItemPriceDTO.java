package com.erp.bakery.model.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ItemPriceDTO {
    private Long id;
    private String itemName;
    private String displayName;
    private String supplier;
    private double purchasePrice;
    private double sellingPrice;
    private boolean status;
    private LocalDate addedDate;


    public ItemPriceDTO(Long id, String itemName, String displayName, String supplier, double purchasePrice, double sellingPrice, boolean status, LocalDate addedDate) {
        this.id = id;
        this.itemName = itemName;
        this.displayName = displayName;
        this.supplier = supplier;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.status = status;
        this.addedDate = addedDate;
    }
}
