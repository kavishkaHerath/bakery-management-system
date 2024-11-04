package com.erp.bakery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_login")
public class UserLogin {
    @Id
    private String userId;
    private String username;
    private String password;
    private String userRole;
}
