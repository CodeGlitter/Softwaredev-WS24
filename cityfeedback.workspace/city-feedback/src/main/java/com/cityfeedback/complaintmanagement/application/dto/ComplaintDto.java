package com.cityfeedback.complaintmanagement.application.dto;

public class ComplaintDto {
    private String title;
    private String description;
    private String location;
    private String createdAt;

    public ComplaintDto(String title, String description, String location, String createdAt) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.createdAt = createdAt;
    }

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
}
