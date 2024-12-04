package com.erp.bakery.service;

import com.erp.bakery.exception.DeletionException;
import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.EmployeeLevel;
import com.erp.bakery.repository.EmployeeLevelRepository;
import com.erp.bakery.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeLevelService {
    @Autowired
    private final EmployeeLevelRepository employeeLevelRepository;
    @Autowired
    private final EmployeeRepository employeeRepository;

    public EmployeeLevelService(EmployeeLevelRepository employeeLevelRepository, EmployeeRepository employeeRepository) {
        this.employeeLevelRepository = employeeLevelRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeLevel> getAllEmployeeLevels() {
        return employeeLevelRepository.findAll();
    }

    public EmployeeLevel saveEmployeeLevel(EmployeeLevel employeeLevel) {
        // Check for duplicate name
        if (employeeLevelRepository.existsByLevelName(employeeLevel.getLevelName())) {
            throw new DuplicateFieldException("Employee Level Name " + employeeLevel.getLevelName() + " is already in use.");
        }
        return employeeLevelRepository.save(employeeLevel);
    }

    public EmployeeLevel updateEmployeeLevel(EmployeeLevel updatedEmployeeLevel) {
        var levelId = updatedEmployeeLevel.getLevelId();
        var levelName = updatedEmployeeLevel.getLevelName();

        EmployeeLevel existingUserRole = employeeLevelRepository.findById(levelId).
                orElseThrow(() -> new RuntimeException("UserRole not found"));

        // Check if email is in use by another supplier
        if (levelName != null && !levelName.equals(existingUserRole.getLevelName()) &&
                employeeLevelRepository.existsByLevelName(levelName)) {
            throw new DuplicateFieldException("Employee Level Name " + levelName + " is already in use.");
        }
        // Update allowed fields
        existingUserRole.setLevelName(updatedEmployeeLevel.getLevelName());
        return employeeLevelRepository.save(existingUserRole);
    }

    public void deleteUserRoleDetails(Long levelId) {
        if (!employeeLevelRepository.existsById(levelId)) {
            throw new NotFoundException("Employee Level not found with Employee Level ID: " + levelId);
        }
        // Check if the category is referenced in the Item table
        boolean isReferenced = employeeRepository.existsByLevel_LevelId(levelId);
        if (isReferenced) {
            throw new DeletionException(
                    "Employee Level with Employee Level Id :" + levelId + " is associated with employees and cannot be deleted."
            );
        }
        employeeLevelRepository.deleteById(levelId);
    }
}
