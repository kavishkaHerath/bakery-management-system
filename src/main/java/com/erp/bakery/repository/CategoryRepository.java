package com.erp.bakery.repository;

import com.erp.bakery.model.Category;
import com.erp.bakery.model.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String categoryName);

    // Custom query to find all categories with status true
    @Query("SELECT new com.erp.bakery.model.CategoryDTO(c.categoryId, c.categoryName) FROM Category c WHERE c.status = true")
    List<CategoryDTO> findActiveCategories();
}
