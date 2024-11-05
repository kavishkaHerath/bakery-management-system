package com.erp.bakery.controller;

import com.erp.bakery.model.Employee;
import com.erp.bakery.model.EmployeeDTO;
import com.erp.bakery.model.EmployeeRegistrationRequest;
import com.erp.bakery.model.UserLogin;
import com.erp.bakery.response.ResponseMessage;
import com.erp.bakery.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all-employee-details")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @PostMapping("/register")
    public Employee registerEmployee(@RequestBody EmployeeRegistrationRequest registrationRequest) {
        Employee employee = registrationRequest.getEmployee();
        UserLogin userLogin = registrationRequest.getUserLogin();

        return employeeService.saveEmployee(employee, userLogin);
    }

    @PutMapping("/editEmployeeDetails")
    public ResponseEntity<ResponseMessage> updateEmployeeDetails(@RequestBody Employee updateRequest) {
        try {
            Employee updatedEmployee = employeeService.updateEmployeeDetails(updateRequest);

            // If update is successful, return 200 status and success message
            ResponseMessage response = new ResponseMessage("success", "Employee updated successfully", updatedEmployee.getUserId());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            // Handle error, return appropriate error response
            ResponseMessage response = new ResponseMessage("error", "Failed to update employee: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
