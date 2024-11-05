package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
public class EmployeeDTO {
    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nic;
    private String email;
    private String roleId;
    private Long levelId;
    private String hireDate;
    private String phone;
    private String address;
    private String addDate;
    private String addBy;

    // Constructor
    public EmployeeDTO(Employee employee) {
        this.userId = employee.getUserId();
        this.firstName = employee.getFirstName();
        this.middleName = employee.getMiddleName();
        this.lastName = employee.getLastName();
        this.nic = employee.getNic();
        this.email = employee.getEmail();
        this.roleId = employee.getRoleId();
        this.levelId = employee.getLevelId();
        this.hireDate = employee.getHireDate();
        this.phone = employee.getPhone();
        this.address = employee.getAddress();
        this.addDate = employee.getAddDate();
        this.addBy = employee.getAddBy();
    }
}
