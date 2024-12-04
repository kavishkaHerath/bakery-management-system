package com.erp.bakery.controller;

import com.erp.bakery.exception.DeletionException;
import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.UserRole;
import com.erp.bakery.response.ResponseMessage;
import com.erp.bakery.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@CrossOrigin("*")
public class UserRoleController {
    @Autowired
    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping("/get-all-user-role")
    public ResponseEntity<List<UserRole>> getAllCategories() {
        List<UserRole> userRoles = userRoleService.getAllUserRole();
        return ResponseEntity.ok(userRoles);
    }

    @PostMapping("/add-user-role")
    public ResponseEntity<?> addUserRole(@RequestBody UserRole userRole) {
        try {
            UserRole userRole1 = userRoleService.saveUserRole(userRole);
            return ResponseEntity.status(HttpStatus.CREATED).body(userRole1);
        } catch (DuplicateFieldException ex) {
            ResponseMessage errorDuplicate = new ResponseMessage(
                    "error-duplicate",
                    ex.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDuplicate);
        } catch (Exception ex) {
            ResponseMessage error = new ResponseMessage(
                    "error",
                    "An unexpected error occurred.",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/editUserRoleDetails")
    public ResponseEntity<?> updateUserRoleDetails(@RequestBody UserRole userRole) {
        try {
            UserRole role = userRoleService.updateUserRole(userRole);

            ResponseMessage response = new ResponseMessage(
                    "success",
                    "User role updated successfully",
                    String.valueOf(userRole.getRoleId())
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DuplicateFieldException ex) {
            ResponseMessage duplicateError = new ResponseMessage(
                    "error-duplicate",
                    ex.getMessage(),
                    String.valueOf(userRole.getRoleId())
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
    @DeleteMapping("/delete/{userRoleId}")
    public ResponseEntity<?> deleteUserRoleDetails(@PathVariable String userRoleId) {
        try {
            userRoleService.deleteUserRoleDetails(userRoleId); // Call service to delete the employee
            return ResponseEntity.status(HttpStatus.OK).body("User role with Role ID " + userRoleId + " deleted successfully.");
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
                    userRoleId.toString()
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
