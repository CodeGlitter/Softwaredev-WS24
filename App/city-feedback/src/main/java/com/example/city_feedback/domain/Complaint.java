package com.example.city_feedback.domain;
/**
 * Represents a complaint filed by a user.
 * Each complaint has an ID, title, description, and mode.
 * By default, new complaints start in draft mode.
 * Once submitted, the complaint mode changes to submitted, making it non-editable.
 */

public class Complaint {
    private int id;
    private String title;
    private String description;
    private String mode; // "draft" or "submitted"

    /**
     * Constructs a new complaint in draft mode.
     * @param id Unique identifier for the complaint.
     * @param title The title of the complaint.
     * @param description The detailed description of the complaint.
     */
    public Complaint(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.mode = "draft"; // Default mode is "draft"
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public int setId(int id) {
        return this.id = id;
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

    public String getMode() {
        return mode;
    }

    /**
     * Submits the complaint, changing the mode to "submitted".
     * Once submitted, the complaint title and description cannot be modified.
     * @throws IllegalStateException if the complaint has already been submitted.
     */
    public void submit() {
        if ("draft".equals(this.mode)) {
            this.mode = "submitted"; // Change mode to submitted
        } else {
            throw new IllegalStateException("Complaint has already been submitted.");
        }
    }
}
