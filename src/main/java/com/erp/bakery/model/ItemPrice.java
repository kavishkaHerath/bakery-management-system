package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class ItemPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "itemId", referencedColumnName = "itemId", nullable = false)
    private Item item;
    @Column(nullable = false)
    private Double purchasePrice;
}
