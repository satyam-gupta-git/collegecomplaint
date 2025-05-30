package com.college.complaint.model;

import java.util.Date;

/**
 * Represents a complaint in the system
 */
public class Complaint {
    private int id;
    private int userId;
    private String title;
    private String description;
    private String category;
    private String location;
    private String imagePath;
    private Integer assignedStaffCategoryId;
    private Date submissionDate;
    private String status; // "pending", "in-progress", "resolved"
    private String resolution;
    private Date resolutionDate;
    
    public Complaint() {
    }
    
    public Complaint(int id, int userId, String title, String description, String category, 
                    String location, String imagePath, Integer assignedStaffCategoryId,
                    Date submissionDate, String status, String resolution, Date resolutionDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.location = location;
        this.imagePath = imagePath;
        this.assignedStaffCategoryId = assignedStaffCategoryId;
        this.submissionDate = submissionDate;
        this.status = status;
        this.resolution = resolution;
        this.resolutionDate = resolutionDate;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public Integer getAssignedStaffCategoryId() {
        return assignedStaffCategoryId;
    }
    
    public void setAssignedStaffCategoryId(Integer assignedStaffCategoryId) {
        this.assignedStaffCategoryId = assignedStaffCategoryId;
    }
    
    public Date getSubmissionDate() {
        return submissionDate;
    }
    
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getResolution() {
        return resolution;
    }
    
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
    
    public Date getResolutionDate() {
        return resolutionDate;
    }
    
    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }
} 