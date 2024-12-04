package com.erp.bakery.controller;

import com.erp.bakery.exception.DeletionException;
import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.EmployeeLevel;
import com.erp.bakery.model.UserRole;
import com.erp.bakery.response.ResponseMessage;
import com.erp.bakery.service.EmployeeLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-level")
@CrossOrigin("*")
public class EmployeeLevelController {
    @Autowired
    private final EmployeeLevelService employeeLevelService;

    public EmployeeLevelController(EmployeeLevelService employeeLevelService) {
        this.employeeLevelService = employeeLevelService;
    }

    @GetMapping("/get-all-employee-level")
    public ResponseEntity<List<EmployeeLevel>> getAllEmployeeLevels() {
        List<EmployeeLevel> employeeLevels = employeeLevelService.getAllEmployeeLevels();
        return ResponseEntity.ok(employeeLevels);
    }

    @PostMapping("/add-employee-levels")
    public ResponseEntity<?> addUserRole(@RequestBody EmployeeLevel employeeLevel) {
        try {
            EmployeeLevel employeeLevel1 = employeeLevelService.saveEmployeeLevel(employeeLevel);
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeLevel1);
        } catch (Exception ex) {
            ResponseMessage error = new ResponseMessage(
                    "error",
                    "An unexpected error occurred.",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

        }
    }

    @PutMapping("/editEmployeeLevelDetails")
    public ResponseEntity<?> updateEmployeeLevelDetails(@RequestBody EmployeeLevel employeeLevel) {
        try {
            EmployeeLevel role = employeeLevelService.updateEmployeeLevel(employeeLevel);

            ResponseMessage response = new ResponseMessage(
                    "success",
                    "Employee Level updated successfully",
                    String.valueOf(employeeLevel.getLevelId())
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DuplicateFieldException ex) {
            ResponseMessage duplicateError = new ResponseMessage(
                    "error-duplicate",
                    ex.getMessage(),
                    String.valueOf(employeeLevel.getLevelId())
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(duplicateError);
        } catch (Exception ex) {
            ResponseMessage error = new ResponseMessage(
                    "error",
                    "Failed to update Category: " + ex.getMessage(),
                    null
            );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Method to delete employee by ID
    @DeleteMapping("/delete/{levelId}")
    public ResponseEntity<?> deleteEmployeeLevelDetails(@PathVariable Long levelId) {
        try {
            employeeLevelService.deleteUserRoleDetails(levelId); // Call service to delete the employee
            return ResponseEntity.status(HttpStatus.OK).body("Employee level with Employee Level ID " + levelId + " deleted successfully.");
        } catch (NotFoundException ex) {
            // Handle employee not found
            ResponseMessage responseMessage = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    null  // No employee code for error response
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        } catch (DeletionException ex) {
            ResponseMessage responseMessage = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    levelId.toString()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        } catch (Exception ex) {
            ResponseMessage error = new ResponseMessage(
                    "error",
                    "Failed to update Category: " + ex.getMessage(),
                    null
            );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
