package com.erp.bakery.controller;

import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
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

    @GetMapping("/getEmployee/{userId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String userId) {
        try {
            EmployeeDTO employeeDTO = employeeService.findEmployeeById(userId);
            return ResponseEntity.ok(employeeDTO);
        } catch (NotFoundException ex) {
            // Handle employee not found
            ResponseMessage responseMessage = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody EmployeeRegistrationRequest registrationRequest) {
        try {
            // Attempt to add the employee
            Employee employee = registrationRequest.getEmployee();
            UserLogin userLogin = registrationRequest.getUserLogin();
            Employee createdEmployee = employeeService.saveEmployee(employee, userLogin);
            ResponseMessage response = new ResponseMessage("success", "Employee added successfully", employee.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (DuplicateFieldException ex) {
            ResponseMessage responseMessage = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        } catch (Exception ex) {
            // Handle any other unexpected exceptions
            ResponseMessage errorResponse = new ResponseMessage(
                    "error",
                    "An unexpected error occurred.",
                    null
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
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
