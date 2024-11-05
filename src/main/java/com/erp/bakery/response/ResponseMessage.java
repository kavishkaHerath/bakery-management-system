package com.erp.bakery.response;

public class ResponseMessage {
    private String status;
    private String message;
    private String employeeCode;

    public ResponseMessage(String status, String message, String employeeCode) {
        this.status = status;
        this.message = message;
        this.employeeCode = employeeCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
}
