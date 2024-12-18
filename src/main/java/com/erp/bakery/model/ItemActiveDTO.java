package com.erp.bakery.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemActiveDTO {
    private Long itemId;
    private String name;
    private double purchasePrice;

    public ItemActiveDTO(Long itemId, String name, double purchasePrice) {
        this.itemId = itemId;
        this.name = name;
        this.purchasePrice = purchasePrice;
    }
}
