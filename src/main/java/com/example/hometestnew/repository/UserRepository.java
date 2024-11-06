package com.example.hometestnew.repository;

import com.example.hometestnew.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findByEmail(String email);


}
