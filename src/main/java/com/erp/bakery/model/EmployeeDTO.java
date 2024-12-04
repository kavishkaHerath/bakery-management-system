package com.erp.bakery.model;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeDTO {
    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private String nic;
    private String email;
    private UserRole role;
    private EmployeeLevel level;
    private String hireDate;
    private String phone;
    private String address;
    private LocalDate addDate;
    private String addedUserId;
    private String imageUrl;

    // Constructor
    public EmployeeDTO(Employee employee) {
        this.userId = employee.getUserId();
        this.firstName = employee.getFirstName();
        this.middleName = employee.getMiddleName();
        this.lastName = employee.getLastName();
        this.dob = employee.getDob();
        this.nic = employee.getNic();
        this.email = employee.getEmail();
        this.role = employee.getRole();
        this.level = employee.getLevel();
        this.hireDate = employee.getHireDate();
        this.phone = employee.getPhone();
        this.address = employee.getAddress();
        this.addDate = employee.getAddDate();
        this.addedUserId = employee.getAddBy();
        this.imageUrl = employee.getImageUrl();
    }
}
