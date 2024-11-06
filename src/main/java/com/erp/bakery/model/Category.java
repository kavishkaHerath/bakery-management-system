package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "supplier", uniqueConstraints = {@UniqueConstraint(columnNames = "categoryName")})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;
    @Column(nullable = false, unique = true)
    private String categoryName;
    @Column(nullable = false)
    private boolean status;
}
