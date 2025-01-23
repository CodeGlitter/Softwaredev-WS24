package com.example.city_feedback.complaintManagement.application.dto;

/**
 * Data Transfer Object for Categories.
 * Encapsulates category details to be shared between layers.
 */
public class CategoryDto {

    private Integer id;
    private String name;
    private String description;

    /**
     * Constructs a new CategoryDto.
     *
     * @param id          the ID of the category
     * @param name        the name of the category
     * @param description the description of the category
     */
    public CategoryDto(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Default constructor for serialization frameworks
    public CategoryDto() {
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
