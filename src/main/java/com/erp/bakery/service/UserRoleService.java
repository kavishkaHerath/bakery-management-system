package com.erp.bakery.service;

import com.erp.bakery.exception.DeletionException;
import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.UserRole;
import com.erp.bakery.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {
    @Autowired
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserRole> getAllUserRole() {
        return userRoleRepository.findAll();
    }

    public UserRole saveUserRole(UserRole userRole) {
        // Check for duplicate name
        if (userRoleRepository.existsByRoleId(userRole.getRoleId())) {
            throw new DuplicateFieldException("User Role Id " + userRole.getRoleId() + " is already in use.");
        }
        if (userRoleRepository.existsByRoleName(userRole.getRoleName())) {
            throw new DuplicateFieldException("User Role Name " + userRole.getRoleName() + " is already in use.");
        }
        return userRoleRepository.save(userRole);
    }

    public UserRole updateUserRole(UserRole updatedUserRole) {
        var roleId = updatedUserRole.getRoleId();
        var roleName = updatedUserRole.getRoleName();

        UserRole existingUserRole = userRoleRepository.findById(roleId).
                orElseThrow(() -> new RuntimeException("UserRole not found"));

        // Check if email is in use by another supplier
        if (roleName != null && !roleName.equals(existingUserRole.getRoleName()) &&
                userRoleRepository.existsByRoleName(roleName)) {
            throw new DuplicateFieldException("UserRole Name " + roleName + " is already in use.");
        }
        // Update allowed fields
        existingUserRole.setRoleName(updatedUserRole.getRoleName());
        existingUserRole.setRoleDescription(updatedUserRole.getRoleDescription());

        return userRoleRepository.save(existingUserRole);
    }

    public void deleteUserRoleDetails(String userRoleId) {
        if (!userRoleRepository.existsById(userRoleId)) {
            throw new NotFoundException("User Role not found with User Role ID: " + userRoleId);
        }
        // Check if the category is referenced in the Item table
//        boolean isReferenced = itemRepository.existsByUserRole_UserRoleId(userRoleId);
//        if (isReferenced) {
//            throw new DeletionException(
//                    "UserRole with UserRole Code :" + userRoleId + " is associated with items and cannot be deleted."
//            );
//        }
        userRoleRepository.deleteById(userRoleId);
    }

}
