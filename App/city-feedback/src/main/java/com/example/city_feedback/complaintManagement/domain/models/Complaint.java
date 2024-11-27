package com.example.city_feedback.complaintManagement.domain.models;

import java.time.LocalDateTime;

import com.example.city_feedback.progressManagement.domain.models.ComplaintProgress;
import com.example.city_feedback.complaintManagement.application.services.ComplaintValidator;

/**
 * Domain entity representing a complaint in the city feedback system.
 *
 * The Complaint class encapsulates details about a complaint, including the
 * title, description, and location, along with the timestamp of creation. The
 * validation logic is delegated to the ComplaintValidator to ensure that
 * complaints meet required standards before creation.
 */
public class Complaint {
    private int id;
    private String title;
    private String description;
    private ComplaintProgress progress;
    private String location;
    private LocalDateTime createdAt;

    // Validator instance for checking field validity
    private static final ComplaintValidator validator = new ComplaintValidator();

    // Private constructor to enforce use of builder
    private Complaint() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Builder class for creating Complaint objects.
     * The builder pattern provides a flexible way to construct complaints,
     * allowing setting of specific fields and ensuring that validation checks
     * are met upon construction.
     */
    public static class ComplaintBuilder {
        private String title;
        private String description;
        private String location;

        public ComplaintBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ComplaintBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ComplaintBuilder withLocation(String location) {
            this.location = location;
            return this;
        }

        /**
         * Builds and validates the Complaint object.
         *
         * @return a valid Complaint object
         */
        public Complaint build() {
            Complaint complaint = new Complaint();
            complaint.title = title;
            complaint.description = description;
            complaint.location = location;

            return complaint;
        }
    }

    /**
     * Validates if the complaint meets the required standards using ComplaintValidator.
     * @return true if all fields are valid, false otherwise.
     */
    public boolean isValid() {
        return validator.isValidTitle(title) &&
                validator.isValidDescription(description) &&
                validator.isValidLocation(location);
    }

    // Getters for accessing complaint properties
    public int getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Creates a new builder instance for constructing Complaint objects.
     * @return a ComplaintBuilder instance for fluent object creation
     */
    public static ComplaintBuilder builder() {
        return new ComplaintBuilder();
    }
}