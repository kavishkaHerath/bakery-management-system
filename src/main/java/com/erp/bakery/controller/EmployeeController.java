package com.erp.bakery.controller;

import com.erp.bakery.model.Employee;
import com.erp.bakery.model.EmployeeRegistrationRequest;
import com.erp.bakery.model.UserLogin;
import com.erp.bakery.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public Employee registerEmployee(@RequestBody EmployeeRegistrationRequest registrationRequest) {
        Employee employee = registrationRequest.getEmployee();
        UserLogin userLogin = registrationRequest.getUserLogin();

        return employeeService.saveEmployee(employee, userLogin);
    }

    @PutMapping("/editEmployeeDetails")
    public String updateEmployeeDetails(@RequestBody Employee updateRequest) {
        return employeeService.updateEmployeeDetails(updateRequest);
    }
}
