package com.app.smarthome.dtos;

import java.io.Serializable;

public class ErrorResponseDTO implements Serializable {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
