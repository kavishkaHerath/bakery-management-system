package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "itemTbl", uniqueConstraints = {@UniqueConstraint(columnNames = "itemName")})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment for primary key
    private Long itemId;
    @Column(nullable = false, unique = true)
    private String itemName;
    @Column(nullable = false)
    private String displayName;
    @Column(nullable = false)
    private Boolean status;
    @Column(nullable = false)
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "supplierCode", referencedColumnName = "supplierCode", nullable = false)
    private Supplier supplier;
    @Column(nullable = false, updatable = false)
    private LocalDate addDate;
    @Column(nullable = false)
    private String addBy;
}
