package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "supplier", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone")
})
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierCode;
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
    @Column(nullable = false)
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "addedUserId", referencedColumnName = "userId", nullable = false)
    private Employee addedEmployee;
    @Column(nullable = false, updatable = false)
    private LocalDate addDate;
    @ManyToOne
    @JoinColumn(name = "modifiedUserId", referencedColumnName = "userId")
    private Employee modifiedEmployee;
    private LocalDate modifyDate;
    private String imageUrl;
}
