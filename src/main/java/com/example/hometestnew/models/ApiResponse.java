package com.example.hometestnew.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ApiResponse {


    private int status;
    private String message;
    private Map<String, Object> data;

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Update to use a custom response structure
    private String email;
    private String password;



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
