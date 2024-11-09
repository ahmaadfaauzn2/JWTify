package com.example.hometestnew.models;


import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequest {

    @Schema(description = "User email address", example = "user@gmail.com")
    private String email;

    @Schema(description = "User password", example = "abcdef1234")
    private String password;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
