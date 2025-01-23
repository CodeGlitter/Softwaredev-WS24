package com.example.city_feedback.complaintManagement.application.dto;

/**
 * Data Transfer Object for Complaints.
 * Encapsulates complaint details to be shared between layers.
 */
public class ComplaintDto {

    private Long id;
    private String title;
    private String description;
    private String location;
    private String createdAt;
    private Integer categoryId;
    private String categoryName;

    /**
     * Constructs a new ComplaintDto.
     *
     * @param id           the ID of the complaint
     * @param title        the title of the complaint
     * @param description  the description of the complaint
     * @param location     the location of the complaint
     * @param createdAt    the creation timestamp of the complaint
     * @param categoryId   the ID of the associated category
     * @param categoryName the name of the associated category
     */
    public ComplaintDto(Long id, String title, String description, String location, String createdAt, Integer categoryId, String categoryName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.createdAt = createdAt;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Default constructor for serialization frameworks
    public ComplaintDto() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
