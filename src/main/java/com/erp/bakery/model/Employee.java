package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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
    @Column(length = 20, nullable = false, unique = true)
    private String nic;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(length = 10, nullable = false, unique = true)
    private String phone;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, length = 3)
    private String roleId;
    @Column(nullable = false)
    private Long levelId;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private String hireDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date addDate = new Date(); // Defaults to current date

    @Column(length = 8, nullable = false)
    private String addBy;
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private UserLogin userLogin;
}
