package com.erp.bakery.controller;

import com.erp.bakery.model.EmployeeLevel;
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
}
