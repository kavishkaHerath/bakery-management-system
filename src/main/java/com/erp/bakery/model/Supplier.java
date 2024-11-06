package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "supplier", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone")
})
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierCode;
    @Column(nullable = false)
    private String companeyName;
    @Column(nullable = false)
    private String companeyRegisterNo;
    @Column(nullable = false)
    private String supplierName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(length = 10, nullable = false, unique = true)
    private String phone;
    @Column(nullable = false)
    private String address;
    private String imageUrl;
}
