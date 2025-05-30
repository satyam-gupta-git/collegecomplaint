package com.college.complaint.model;

import java.util.Date;

/**
 * Represents a user in the system
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String userType; // "student", "staff", or "admin"
    private Integer staffCategoryId;
    private String registrationStatus; // "pending", "approved", "rejected"
    private Date createdAt;
    
    public User() {
    }
    
    public User(int id, String username, String password, String fullName, String email, String userType, Integer staffCategoryId, String registrationStatus) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.userType = userType;
        this.staffCategoryId = staffCategoryId;
        this.registrationStatus = registrationStatus;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUserType() {
        return userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public Integer getStaffCategoryId() {
        return staffCategoryId;
    }
    
    public void setStaffCategoryId(Integer staffCategoryId) {
        this.staffCategoryId = staffCategoryId;
    }
    
    public String getRegistrationStatus() {
        return registrationStatus;
    }
    
    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    // Validation methods
    public boolean isValid() {
        return username != null && !username.trim().isEmpty() &&
               password != null && !password.trim().isEmpty() &&
               fullName != null && !fullName.trim().isEmpty() &&
               email != null && !email.trim().isEmpty() &&
               userType != null && (userType.equals("student") || userType.equals("staff") || userType.equals("admin"));
    }
} 