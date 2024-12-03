package com.erp.bakery.controller;

import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.model.Category;
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
