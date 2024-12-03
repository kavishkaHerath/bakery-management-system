package com.erp.bakery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserRole {
    @Id
    @Column(length = 8)
    private String roleId;
    @Column(nullable = false, unique = true)
    private String roleName;
    @Column(nullable = false)
    private String roleDescription;
}
