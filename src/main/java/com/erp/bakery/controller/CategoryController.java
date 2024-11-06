package com.erp.bakery.controller;

import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.model.Category;
import com.erp.bakery.model.Supplier;
import com.erp.bakery.response.ResponseMessage;
import com.erp.bakery.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                    "error-dulplicate",
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
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }
}
