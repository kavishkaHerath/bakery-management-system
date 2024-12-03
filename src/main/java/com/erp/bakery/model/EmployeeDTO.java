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
    private String roleId;
    private Long levelId;
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
        this.roleId = employee.getRoleId();
        this.levelId = employee.getLevelId();
        this.hireDate = employee.getHireDate();
        this.phone = employee.getPhone();
        this.address = employee.getAddress();
        this.addDate = employee.getAddDate();
        this.addedUserId = employee.getAddBy();
        this.imageUrl = employee.getImageUrl();
    }
}
