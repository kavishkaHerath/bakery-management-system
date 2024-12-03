package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class EmployeeLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long levelId;
    @Column(nullable = false, unique = true)
    private String levelName;
}
