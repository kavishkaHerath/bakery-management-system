package com.erp.bakery.service;

import com.erp.bakery.exception.DeletionException;
import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.Category;
import com.erp.bakery.model.CategoryDTO;
import com.erp.bakery.repository.CategoryRepository;
import com.erp.bakery.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    public final CategoryRepository categoryRepository;
    @Autowired
    private final ItemRepository itemRepository;

    public CategoryService(CategoryRepository categoryRepository, ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }


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

    public List<CategoryDTO> getActiveCategories() {
        return categoryRepository.findActiveCategories();
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
        // Check if the category is referenced in the Item table
        boolean isReferenced = itemRepository.existsByCategory_CategoryId(categoryCode);
        if (isReferenced) {
            throw new DeletionException(
                    "Category with Category Code :" + categoryCode + " is associated with items and cannot be deleted."
            );
        }
        categoryRepository.deleteById(categoryCode);
    }
}
