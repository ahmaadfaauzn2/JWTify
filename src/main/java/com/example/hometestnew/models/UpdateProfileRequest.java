package com.example.hometestnew.models;

public class UpdateProfileRequest {
    private String firstName; // Updated field name
    private String lastName;  // New field for last name
    private String profileImage; // New field for profile image URL

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}

