package com.example.hometestnew.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")

public class User {


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Schema(hidden = true)
    @JsonIgnore
    private String transactionType;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    @Schema(hidden = true)
    private Long id;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Transaction> transactions = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Schema(hidden = true)
    private List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setUser(this); // Set the user reference in the transaction
    }
    @Schema(hidden = true)

    public String getInvoiceNumber() {
        return invoiceNumber;
    }
//    public List<Transaction> getTransactions() {
//        return transactions;
//    }
//    public void addTransaction(Transaction transaction) {
//        transactions.add(transaction);
//        transaction.setUser(this); // Set the user reference in the transaction
//    }
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @ElementCollection
    @Schema(hidden = true)
    private List<String> transactionHistory = new ArrayList<>();

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Transaction fields
    @Schema(hidden = true)
    @JsonIgnore
    private String invoiceNumber;

    @Schema(hidden = true)
    @JsonIgnore
    private String description;

    @Schema(hidden = true)
    @JsonIgnore
    private double totalAmount;


    @Email
    @NotEmpty
    @Schema(description = "user@gmail.com", example = "")
    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    @NotEmpty
    private String firstName;

    @JsonProperty("last_name")
    @NotEmpty
    private String lastName;


    @Size(min = 8)
    @JsonProperty("password")
    @NotEmpty
    private String password;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    private String profileImage;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    private double balance;

//    // New fields
//    private String bannerName;
//    private String bannerImage;

//    public String getBannerName() {
//        return bannerName;
//    }
//
//    public void setBannerName(String bannerName) {
//        this.bannerName = bannerName;
//    }
//
//    public String getBannerImage() {
//        return bannerImage;
//    }
//
//    public void setBannerImage(String bannerImage) {
//        this.bannerImage = bannerImage;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    @Column(name = "created_on")
    private ZonedDateTime createdOn;


    public User(String email, double balance, String invoiceNumber, String transactionType, String description, double totalAmount, ZonedDateTime createdOn) {
        this.email = email;
        this.balance = balance;
        this.invoiceNumber = invoiceNumber;
        this.transactionType = transactionType;
        this.description = description;
        this.totalAmount = totalAmount;
        this.createdOn = createdOn;
    }
    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_USER"); // Example: replace with your actual roles
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Object getTransactionCount() {
        return null;
    }
}


