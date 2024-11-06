package com.example.hometestnew.Service;

import com.example.hometestnew.models.UpdateProfileRequest;
import com.example.hometestnew.models.User;
import com.example.hometestnew.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository to interact with the database

    public User updateUserProfile(String email, UpdateProfileRequest updateProfileRequest) {
        // Find the user by email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Update user fields
        user.setFirstName(updateProfileRequest.getFirstName());
        user.setLastName(updateProfileRequest.getLastName());
        user.setProfileImage(updateProfileRequest.getProfileImage()); // Assuming you want to update the profile image

        return userRepository.save(user); // Save and return the updated user
    }
}