package com.example.hometestnew.repository;

import com.example.hometestnew.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<com.example.hometestnew.models.Service> findAll(); // This method is inherited from JpaRepository
    Service findByServiceCode(String serviceCode);
}
