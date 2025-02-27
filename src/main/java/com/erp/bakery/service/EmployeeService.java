package com.erp.bakery.service;

import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.Employee;
import com.erp.bakery.model.EmployeeDTO;
import com.erp.bakery.model.UserLogin;
import com.erp.bakery.repository.EmployeeRepository;
import com.erp.bakery.repository.UserLoginRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final UserLoginRepository userLoginRepository;

    public EmployeeService(EmployeeRepository employeeRepository, UserLoginRepository userLoginRepository) {
        this.employeeRepository = employeeRepository;
        this.userLoginRepository = userLoginRepository;
    }

    // Generate user ID based on role
    private String generateUserId(String role) {
        long count = employeeRepository.countByRoleId(role) + 1;  // Get count based on role and increment by 1
        return String.format("%s%05d", role, count);  // Format to 5 digits, e.g., MGR00001
    }
    @Transactional
    public Employee saveEmployee(Employee employee, UserLogin userLogin) {
        // Check if NIC already exists in the database
        if (employeeRepository.existsByNic(employee.getNic())) {
            throw new DuplicateFieldException("NIC " + employee.getNic() + " is already in use.");
        }
        // Check if email already exists in the database
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new DuplicateFieldException("Email " + employee.getEmail() + " is already in use.");
        }
        // Check if phone number already exists in the database
        if (employeeRepository.existsByPhone(employee.getPhone())) {
            throw new DuplicateFieldException("Phone number " + employee.getPhone() + " is already in use.");
        }
        // Check if phone number already exists in the database
        if (userLoginRepository.existsByUsername(userLogin.getUsername())) {
            throw new DuplicateFieldException("Username " + userLogin.getUsername() + " is already in use.");
        }
        // Generate custom user ID
        String userId = generateUserId(userLogin.getUserRole());
        employee.setUserId(userId);
        // Set to current date without time
        employee.setAddDate(LocalDate.now());

        userLogin.setUserId(userId);

        // Save Employee and UserLogin records
        Employee savedEmployee = employeeRepository.save(employee);
        userLoginRepository.save(userLogin);
        return savedEmployee;
    }

    //Find all employee details
    public List<EmployeeDTO> findAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeDTO::new)  // Convert each Employee to EmployeeDTO
                .collect(Collectors.toList());
    }

    // Method to find an employee by ID
    public EmployeeDTO findEmployeeById(String userId) {
        return new EmployeeDTO(employeeRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Employee not found with Employee code: " + userId)));
    }

    // Update employee details
    public Employee updateEmployeeDetails(Employee updateRequest) {
        var userId = updateRequest.getUserId();
        var nic = updateRequest.getNic();
        var email = updateRequest.getEmail();
        var phone = updateRequest.getPhone();
        Employee existingEmployee = employeeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found with Employee code: " + userId));
        // Check if nic is in use by another employee
        if (nic != null && !nic.equals(existingEmployee.getNic()) &&
                employeeRepository.existsByNic(nic)) {
            throw new DuplicateFieldException("NIC " + nic + " is already in use.");
        }
        // Check if email is in use by another employee
        if (email != null && !email.equals(existingEmployee.getEmail()) &&
                employeeRepository.existsByEmail(email)) {
            throw new DuplicateFieldException("Email " + email + " is already in use.");
        }
        // Check if phone number is in use by another employee
        if (phone != null && !phone.equals(existingEmployee.getPhone()) &&
                employeeRepository.existsByPhone(phone)) {
            throw new DuplicateFieldException("Phone number " + phone + " is already in use.");
        }
        // Update only specified fields
        if (updateRequest.getFirstName() != null) {
            existingEmployee.setFirstName(updateRequest.getFirstName());
        }
        if (updateRequest.getMiddleName() != null) {
            existingEmployee.setMiddleName(updateRequest.getMiddleName());
        }
        if (updateRequest.getLastName() != null) {
            existingEmployee.setLastName(updateRequest.getLastName());
        }
        if (updateRequest.getDob() != null) {
            existingEmployee.setDob(updateRequest.getDob());
        }
        if (nic != null) {
            existingEmployee.setNic(nic);
        }
        if (email != null) {
            existingEmployee.setEmail(email);
        }
        if (phone != null) {
            existingEmployee.setPhone(phone);
        }
        if (updateRequest.getAddress() != null) {
            existingEmployee.setAddress(updateRequest.getAddress());
        }
        if (updateRequest.getRole() != null) {
            existingEmployee.setRole(updateRequest.getRole());
        }
        if (updateRequest.getLevel() != null) {
            existingEmployee.setLevel(updateRequest.getLevel());
        }
        if (updateRequest.getHireDate() != null) {
            existingEmployee.setHireDate(updateRequest.getHireDate());
        }
        if (updateRequest.getImageUrl() != null) {
            existingEmployee.setImageUrl(updateRequest.getImageUrl());
        }
        if (updateRequest.getModifyBy() != null) {
            existingEmployee.setModifyBy(updateRequest.getModifyBy());
        }
        // Set to current date without time
        existingEmployee.setModifyDate(LocalDate.now());
        return employeeRepository.save(existingEmployee);
    }

    // Method to delete an employee by ID
    public void deleteEmployeeById(String userId) {
        if (!employeeRepository.existsById(userId)) {
            throw new NotFoundException("Employee not found with Employee code: " + userId);
        }
        employeeRepository.deleteById(userId); // Deletes the employee from the database
        userLoginRepository.deleteById(userId);
    }
}
