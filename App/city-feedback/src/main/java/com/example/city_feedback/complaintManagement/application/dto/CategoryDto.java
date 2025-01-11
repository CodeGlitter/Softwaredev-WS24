package com.example.city_feedback.complaintManagement.application.dto;

/**
 * Data Transfer Object for the Category entity.
 */
public class CategoryDto {
    private int id; // The ID of the category
    private String name; // The name of the category
    private String description; // The description of the category

    // Constructor
    public CategoryDto(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
