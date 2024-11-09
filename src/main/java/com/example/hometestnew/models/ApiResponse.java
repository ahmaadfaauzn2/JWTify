package com.example.hometestnew.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public class ApiResponse {


    private int status;
    private String message;
    private Map<String, Object> data;

    // @Schema(description = "user@gmail.com", example = "usertesting@gmail.com")
    // @JsonProperty("email")
    // public String getEmail() {
    //     return email;
    // }

    // public void setEmail(String email) {
    //     this.email = email;
    // }

    // @JsonProperty("password")
    // @Schema(description = "The password of the user", example = "abcdef1234")
    // public String getPassword() {
    //     return password;
    // }

    // public void setPassword(String password) {
    //     this.password = password;
    // }

    // Update to use a custom response structure
    // @Schema(description = "user@gmail.com", example = "usertesting@gmail.com")
    // private String email;

    // @Schema(description = "The password of the user", example = "abcdef1234")
    // @JsonProperty("password")
    // private String password;



    public ApiResponse(int status, String message, Map<String, Object> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
