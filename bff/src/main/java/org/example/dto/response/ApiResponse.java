package org.example.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ApiResponse implements Serializable {
    private String message;
    private boolean success;
    private Object data;

    public ApiResponse(boolean success) {
        this.success = success;
    }

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }


    public ApiResponse(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponse(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }
}
