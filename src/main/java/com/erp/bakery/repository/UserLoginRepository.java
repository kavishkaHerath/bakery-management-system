package com.erp.bakery.repository;

import com.erp.bakery.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLogin, String> {
    boolean existsByUsername(String username);
}
