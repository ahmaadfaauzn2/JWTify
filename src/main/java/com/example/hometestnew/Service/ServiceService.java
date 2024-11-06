package com.example.hometestnew.Service;


import com.example.hometestnew.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    public List<com.example.hometestnew.models.Service> getAllServices(){
        return serviceRepository.findAll();
    }
}
