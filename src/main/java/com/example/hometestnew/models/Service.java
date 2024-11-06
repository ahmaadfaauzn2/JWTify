package com.example.hometestnew.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

//    public String getServiceDescription() {
//        return serviceDescription;
//    }
//
//    public void setServiceDescription(String serviceDescription) {
//        this.serviceDescription = serviceDescription;
//    }

    public String getServiceIcon() {
        return serviceIcon;
    }

    public void setServiceIcon(String serviceIcon) {
        this.serviceIcon = serviceIcon;
    }

    public double getServiceTariff() {
        return serviceTariff;
    }

    public void setServiceTariff(int serviceTariff) {
        this.serviceTariff = serviceTariff;
    }

    private String serviceCode; // Add service code
    private String serviceName;
//    private String serviceDescription;
    private String serviceIcon; // Add service icon URL
    @Column(name = "service_tariff", nullable = false) // Ensure the column is NOT NULL
    private int serviceTariff; // Add service tariff


}
