package com.erp.bakery.controller;

import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.Category;
import com.erp.bakery.response.ResponseMessage;
import com.erp.bakery.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add-category")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        try {
            Category newCategory = categoryService.addCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
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

    @GetMapping("/get-all-category")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("getCategory/{categoryCode}")
    public ResponseEntity<?> getSupplierByCode(@PathVariable Long categoryCode) {
        try {
            Category category = categoryService.getCategoryByCode(categoryCode);
            return ResponseEntity.ok(category);
        } catch (NotFoundException ex) {
            ResponseMessage responseMessage = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }

    @GetMapping("getCategory/active")
    public ResponseEntity<List<Map<String, Object>>> getActiveCategories() {
        List<Map<String, Object>> activeCategories = categoryService.getActiveCategories();
        return ResponseEntity.ok(activeCategories);
    }

    @PutMapping("/editCategoryDetails")
    public ResponseEntity<?> updateCategoryDetails(@RequestBody Category updatedCategory) {
        try {
            Category category = categoryService.updateCategory(updatedCategory);

            ResponseMessage response = new ResponseMessage(
                    "success",
                    "Category updated successfully",
                    String.valueOf(updatedCategory.getCategoryId())
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DuplicateFieldException ex) {
            ResponseMessage duplicateError = new ResponseMessage(
                    "error-duplicate",
                    ex.getMessage(),
                    String.valueOf(updatedCategory.getCategoryId())
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
    @DeleteMapping("/delete/{categoryCode}")
    public ResponseEntity<?> deleteCategoryDetails(@PathVariable Long categoryCode) {
        try {
            categoryService.deleteCategoryDetails(categoryCode); // Call service to delete the employee
            return ResponseEntity.status(HttpStatus.OK).body("Category with Category Code " + categoryCode + " deleted successfully.");
        } catch (NotFoundException ex) {
            // Handle employee not found
            ResponseMessage responseMessage = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    null  // No employee code for error response
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }
}
