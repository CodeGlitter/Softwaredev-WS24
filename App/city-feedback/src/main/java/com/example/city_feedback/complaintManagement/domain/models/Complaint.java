package com.example.city_feedback.complaintManagement.domain.models;

import java.time.LocalDateTime;

import com.example.city_feedback.authentication.domain.models.User;
import com.example.city_feedback.progressManagement.domain.models.ComplaintProgress;
import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import jakarta.persistence.*;

/**
 * Domain entity representing a complaint in the city feedback system.
 * The Complaint class encapsulates details about a complaint, including the
 * title, description, and location, along with the timestamp of creation. The
 * validation logic is delegated to the ComplaintValidator to ensure that
 * complaints meet required standards before creation.
 */
@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_progress_id", nullable = false)
    private ComplaintProgress progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true) // Add this if complaints belong to a user
    private User user;


    public Complaint() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Creates a new builder instance for constructing Complaint objects.
     * @return a ComplaintBuilder instance for fluent object creation
     */
    public static ComplaintBuilder builder() {
        return new ComplaintBuilder();
    }

    /**
     * Updates the `updatedAt` timestamp to the current time.
     */
    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        private Location location;
        private Category category;
        private User user;
        private User creator;

        public ComplaintBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ComplaintBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ComplaintBuilder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public ComplaintBuilder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public ComplaintBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public ComplaintBuilder withCreator(User creator) {
            this.creator = creator;
            return this;
        }

        /**
         * Builds and validates the Complaint object.
         *
         * @return a valid Complaint object
         */
        public Complaint build() {
            validateFields();

            Complaint complaint = new Complaint();
            complaint.title = title;
            complaint.description = description;
            complaint.location = location;
            complaint.category = category;

            return complaint;
        }

        /**
         * Validates the fields of the complaint before building.
         */
        private void validateFields() {
            if (title == null || title.length() < 5 || title.length() > 100) {
                throw new IllegalArgumentException("Titel bitte 5-10 Zeichen.");
            }
            if (description == null || description.length() < 10 || description.length() > 1000) {
                throw new IllegalArgumentException("Beschreibung bitte 10-1000 Zeichen.");
            }
            if (location == null) {
                throw new IllegalArgumentException("Standortinformationen sind Pflichtfelder.");
            }
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public User getCreator() {
            return creator;
        }

        public void setCreator(User creator) {
            this.creator = creator;
        }
    }

    /**
     * Validates if the complaint meets the required standards.
     * @return true if all fields are valid, false otherwise.
     */
    public boolean isValid() {
        return title != null && title.length() >= 5 && title.length() <= 100 &&
                description != null && description.length() >= 10 && description.length() <= 1000 &&
                location != null && location.isValid();
    }


    // Getters for accessing complaint properties
    public Long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ComplaintProgress getProgress() {
        return progress;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}