package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "categoryTbl", uniqueConstraints = {@UniqueConstraint(columnNames = "categoryName")})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(nullable = false, unique = true)
    private String categoryName;
    @Column(nullable = false)
    private Boolean status;
}
