package com.erp.bakery.response;

public class ResponseMessage {
    private String status;
    private String message;
    private String id;

    public ResponseMessage(String status, String message, String code) {
        this.status = status;
        this.message = message;
        this.id = code;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
