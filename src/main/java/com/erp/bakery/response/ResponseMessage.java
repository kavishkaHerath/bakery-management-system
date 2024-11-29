package com.erp.bakery.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseMessage {
    private String status;
    private String message;
    private String id;

    public ResponseMessage(String status, String message, String code) {
        this.status = status;
        this.message = message;
        this.id = code;
    }

}
