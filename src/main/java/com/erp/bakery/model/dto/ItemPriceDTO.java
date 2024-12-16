package com.erp.bakery.model.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ItemPriceDTO {
    private Long id;
    private Long itemId;
    private String itemName;
    private String displayName;
    private String supplier;
    private double purchasePrice;
    private double sellingPrice;
    private boolean status;
    private LocalDate addedDate;
    private String addedEmployee;
    private LocalDate modifiedDate;
    private String modifiedEmployee;


    public ItemPriceDTO(Long id, Long itemId, String itemName, String displayName, String supplier, double purchasePrice,
                        double sellingPrice, boolean status, LocalDate addedDate,
                        String addedEmployee, LocalDate modifiedDate, String modifiedEmployee) {
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.displayName = displayName;
        this.supplier = supplier;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.status = status;
        this.addedDate = addedDate;
        this.addedEmployee = addedEmployee;
        this.modifiedDate = modifiedDate;
        this.modifiedEmployee = modifiedEmployee;
    }
}
