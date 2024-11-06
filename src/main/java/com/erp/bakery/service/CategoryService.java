package com.erp.bakery.service;

import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.model.Category;
import com.erp.bakery.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        // Check for duplicate name
        if (categoryRepository.existsByName(category.getCategoryName())) {
            throw new DuplicateFieldException(category.getCategoryName() + " is already in use.");
        }
        return categoryRepository.save(category);
    }
}
