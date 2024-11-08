package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ItemOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @ManyToOne
    @JoinColumn(name = "itemOrderCode", nullable = false)
    private ItemsOrder itemOrder; // FK to ItemOrder table
    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private Item item;
    @Column(nullable = false)
    private double unitPrice;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private double totalPrice;
}
