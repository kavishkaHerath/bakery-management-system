package com.erp.bakery.service;

import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.Category;
import com.erp.bakery.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        // Check for duplicate name
        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new DuplicateFieldException(category.getCategoryName() + " is already in use.");
        }
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Map<String, Object>> getActiveCategories() {
        List<Object[]> results = categoryRepository.findActiveCategories();
        // Map results to a structured list of key-value pairs
        return results
                .stream()
                .map(
                        result -> {
                            Map<String, Object> categoryMap = new HashMap<>();
                            categoryMap.put("categoryId", result[0]);
                            categoryMap.put("categoryName", result[1]);
                            return categoryMap;
                        }
                )
                .collect(Collectors.toList());
    }

    public Category getCategoryByCode(Long categoryCode) {
        return categoryRepository.findById(categoryCode).orElseThrow(
                () -> new NotFoundException("Category not found with Category Code: " + categoryCode)
        );
    }

    public Category updateCategory(Category updatedCategory) {
        var categoryId = updatedCategory.getCategoryId();
        var categoryName = updatedCategory.getCategoryName();

        Category existingCategory = categoryRepository.findById(categoryId).
                orElseThrow(() -> new RuntimeException("Category not found"));


        // Check if email is in use by another supplier
        if (categoryName != null && !categoryName.equals(existingCategory.getCategoryName()) &&
                categoryRepository.existsByCategoryName(categoryName)) {
            throw new DuplicateFieldException("Category Name " + categoryName + " is already in use.");
        }
        // Update allowed fields
        existingCategory.setCategoryName(updatedCategory.getCategoryName());
        existingCategory.setStatus(updatedCategory.getStatus());

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategoryDetails(Long categoryCode) {
        if (!categoryRepository.existsById(categoryCode)) {
            throw new NotFoundException("Category not found with Category code: " + categoryCode);
        }
        categoryRepository.deleteById(categoryCode);
    }
}
