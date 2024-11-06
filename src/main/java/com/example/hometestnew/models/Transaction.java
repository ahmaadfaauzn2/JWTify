package com.example.hometestnew.models;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String invoiceNumber;
    private String transactionType;
    private String description;
    private double totalAmount;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    // Constructors, Getters, and Setters
    public Transaction() {}

    public Transaction(User user, String invoiceNumber, String transactionType, String description, double totalAmount, ZonedDateTime createdOn) {
        this.user = user;
        this.invoiceNumber = invoiceNumber;
        this.transactionType = transactionType;
        this.description = description;
        this.totalAmount = totalAmount;
        this.createdOn = createdOn;
    }

    // Getters and Setters...
}