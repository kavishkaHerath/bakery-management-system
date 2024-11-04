package com.erp.bakery.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_login", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class UserLogin {
    @Id
    @Column(length = 8)
    private String userId;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(length = 3, nullable = false)
    private String userRole;
    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private Employee employee;
}
