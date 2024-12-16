package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

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
    private boolean status;
    @Column(nullable = false)
    private double purchasePrice;
    @Column(nullable = false)
    private double sellingPrice;
    @Column(nullable = false)
    private LocalDate addedDate;
    @ManyToOne
    @JoinColumn(name = "addedUser", referencedColumnName = "userId", nullable = false)
    Employee addEmployee;
    private LocalDate modifyDate;
    @ManyToOne
    @JoinColumn(name = "modifiedUser", referencedColumnName = "userId")
    Employee modifyEmployee;
}
