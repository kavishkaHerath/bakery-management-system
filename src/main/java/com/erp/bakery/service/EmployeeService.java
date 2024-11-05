package com.erp.bakery.service;

import com.erp.bakery.model.Employee;
import com.erp.bakery.model.EmployeeDTO;
import com.erp.bakery.model.UserLogin;
import com.erp.bakery.repository.EmployeeRepository;
import com.erp.bakery.repository.UserLoginRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    // Generate user ID based on role
    private String generateUserId(String role) {
        System.out.println(employeeRepository.countByRoleId(role));
        long count = employeeRepository.countByRoleId(role) + 1;  // Get count based on role and increment by 1
        System.out.println(count);
        return String.format("%s%05d", role, count);  // Format to 5 digits, e.g., MGR00001
    }
    @Transactional
    public Employee saveEmployee(Employee employee, UserLogin userLogin) {
        // Generate custom user ID
        String userId = generateUserId(userLogin.getUserRole());
        employee.setUserId(userId);
        employee.setAddDate(LocalDate.now());// Set to current date without time
        userLogin.setUserId(userId);

        // Save Employee and UserLogin records
        Employee savedEmployee = employeeRepository.save(employee);
        userLoginRepository.save(userLogin);
        return savedEmployee;
    }

    public List<EmployeeDTO> findAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeDTO::new)  // Convert each Employee to EmployeeDTO
                .collect(Collectors.toList());
    }

    public Employee updateEmployeeDetails(Employee updateRequest) {
        var userId = updateRequest.getUserId();
        Employee existingEmployee = employeeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found with userId: " + userId));

        // Update only specified fields
        if (updateRequest.getEmail() != null) {
            existingEmployee.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getPhone() != null) {
            existingEmployee.setPhone(updateRequest.getPhone());
        }
        if (updateRequest.getAddress() != null) {
            existingEmployee.setAddress(updateRequest.getAddress());
        }

        return employeeRepository.save(existingEmployee);
    }
}
