package com.example.city_feedback.complaintManagement.application.dto;

public class ComplaintDto {
    private String title;
    private String description;
    private String location;
    private String createdAt;
    private int categoryId;
    private String categoryName;

    public ComplaintDto(String title, String description, String location, String createdAt, int categoryId, String categoryName) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.createdAt = createdAt;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters for all fields
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName; // Getter for category name
    }


}
