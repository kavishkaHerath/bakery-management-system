package com.erp.bakery.repository;

import com.erp.bakery.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String categoryName);

    // Custom query to find all categories with status true
    @Query("SELECT c.categoryId, c.categoryName FROM categoryTbl c WHERE c.status = true")
    List<Object[]> findActiveCategories();
}
