package com.erp.bakery.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDTO {
    // Getters and Setters
    private Long categoryId;
    private String categoryName;

    // Constructor
    public CategoryDTO(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

}

