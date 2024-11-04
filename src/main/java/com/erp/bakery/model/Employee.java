package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    private String userId;

    private String firstName;
    private String middleName;
    private String lastName;
    private String NIC;
    private String email;
    private String roleId;
    private Long levelId;
    private String hireDate;
    private String phone;
    private String address;
    private String addDate;
    private String addBy;
}
