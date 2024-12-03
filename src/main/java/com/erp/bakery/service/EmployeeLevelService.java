package com.erp.bakery.service;

import com.erp.bakery.model.EmployeeLevel;
import com.erp.bakery.repository.EmployeeLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeLevelService {
    @Autowired
    private final EmployeeLevelRepository employeeLevelRepository;

    public EmployeeLevelService(EmployeeLevelRepository employeeLevelRepository) {
        this.employeeLevelRepository = employeeLevelRepository;
    }

    public List<EmployeeLevel> getAllEmployeeLevels() {
        return employeeLevelRepository.findAll();
    }

    public EmployeeLevel saveEmployeeLevel(EmployeeLevel employeeLevel) {
        return employeeLevelRepository.save(employeeLevel);
    }
}
