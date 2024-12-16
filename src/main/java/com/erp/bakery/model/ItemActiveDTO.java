package com.erp.bakery.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemActiveDTO {
    //    private Long itemId;
//    private String name;
//
//    public ItemActiveDTO(Long itemId, String name) {
//        this.itemId = itemId;
//        this.name = name;
//    }
    private Long itemId;
    private String name;
    private double purchasePrice;
    private double sellingPrice;

    public ItemActiveDTO(Long itemId, String name, double purchasePrice, double sellingPrice) {
        this.itemId = itemId;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }
}
