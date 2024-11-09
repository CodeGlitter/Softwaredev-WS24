package com.example.city_feedback.domain;

import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Domain entity representing a complaint in the city feedback system.
 * Focused on the core functionality of creating and validating complaints.
 */
public class Complaint {
    private int id;
    private String title;
    private String description;
    private String location;
    private ComplaintProgress progress;
    private LocalDateTime createdAt;

    // Advanced Java Feature: Lambda for input validation
    private static final Predicate<String> isNotEmpty = str -> str != null && !str.trim().isEmpty();

    // Private constructor to enforce use of builder
    private Complaint() {
        this.createdAt = LocalDateTime.now();
        this.progress = ComplaintProgress.NEW;
    }

    /**
     * Builder class for creating Complaint objects.
     * Implements the Builder pattern for flexible object creation.
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

        public Complaint build() {
            Complaint complaint = new Complaint();
            complaint.title = title;
            complaint.description = description;
            complaint.location = location;
            return complaint;
        }
    }

    /**
     * Validates if the complaint has all required fields properly set
     * Uses lambda predicate for validation
     */
    public boolean isValid() {
        return isNotEmpty.test(title) &&
                isNotEmpty.test(description) &&
                isNotEmpty.test(location);
    }

    // Getters
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

    public ComplaintProgress getProgress() {
        return progress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Creates a new builder instance for complaint creation
     */
    public static ComplaintBuilder builder() {
        return new ComplaintBuilder();
    }
}