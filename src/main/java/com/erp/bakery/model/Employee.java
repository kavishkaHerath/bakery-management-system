package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "employee", uniqueConstraints = {
        @UniqueConstraint(columnNames = "NIC"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone")
})
public class Employee {
    @Id
    @Column(length = 8)
    private String userId;
    @Column(nullable = false)
    private String firstName;
    private String middleName;
    @Column(nullable = false)
    private String lastName;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private String dob;
    @Column(length = 20, nullable = false, unique = true)
    private String nic;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(length = 10, nullable = false, unique = true)
    private String phone;
    @Column(nullable = false)
    private String address;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private String hireDate;
    @Column(nullable = false, updatable = false)
    private LocalDate addDate;
    @Column(nullable = false)
    private String imageUrl;
    @Column(length = 8, nullable = false)
    private String addBy;
    @Column(length = 8)
    private String modifyBy;
    private LocalDate modifyDate;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "roleId", nullable = false)
    private UserRole role;
    @ManyToOne
    @JoinColumn(name = "levelId", referencedColumnName = "levelId", nullable = false)
    private EmployeeLevel level;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private UserLogin userLogin;
}
