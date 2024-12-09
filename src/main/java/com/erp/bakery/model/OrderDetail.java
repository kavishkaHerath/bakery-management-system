package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @ManyToOne
    @JoinColumn(name = "itemId", referencedColumnName = "itemId", nullable = false)
    private Item item; // FK to Item table

    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private double unitPrice;
    @Column(nullable = false)
    private double totalPrice;
    @Column(nullable = false)
    private String requestBy;
}
