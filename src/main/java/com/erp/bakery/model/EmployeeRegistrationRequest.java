package com.erp.bakery.model;

import lombok.Data;

@Data
public class EmployeeRegistrationRequest {
    private Employee employee;
    private UserLogin userLogin;
}
