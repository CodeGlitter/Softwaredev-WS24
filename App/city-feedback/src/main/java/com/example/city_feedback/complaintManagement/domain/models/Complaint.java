package com.example.city_feedback.complaintManagement.domain.models;

import java.time.LocalDateTime;
import com.example.city_feedback.authentication.domain.models.User;
import com.example.city_feedback.progressManagement.domain.models.ComplaintProgress;
import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import jakarta.persistence.*;

/**
 * Domain entity representing a complaint in the city feedback system.
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
    @JoinColumn(name = "complaint_progress_id", nullable = true)
    private ComplaintProgress progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "creator_id", nullable = false, updatable = false)
    private Long creatorId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Default constructor for JPA and other potential uses
    public Complaint() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Additional constructor used in the app
    public Complaint(Long id, String title, String description, Location location, User user, Category category, ComplaintProgress progress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.user = user;
        this.category = category;
        this.progress = progress;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Factory method using a builder for flexible complaint creation
    public static ComplaintBuilder builder() {
        return new ComplaintBuilder();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    private void validate() {
        if (title == null || title.length() < 5 || title.length() > 100) {
            throw new IllegalArgumentException("Titel bitte 5-100 Zeichen.");
        }
        if (description == null || description.length() < 10 || description.length() > 1000) {
            throw new IllegalArgumentException("Beschreibung bitte 10-1000 Zeichen.");
        }
        if (location == null || !location.isValid()) {
            throw new IllegalArgumentException("Pflichtfeld und gültige Zeichen bitte.");
        }
        if (user == null) {
            throw new IllegalArgumentException("Benutzer ist erforderlich.");
        }
    }

    // Getters and setters (required by other parts of the app)
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Location getLocation() {
        return location;
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

    public Category getCategory() {
        return category;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setProgress(ComplaintProgress progress) {
        this.progress = progress;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatorId(long id) {
        this.creatorId = id;
    }

    public boolean isValid() {
        try {
            validate();
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    // Builder class for flexible object creation
    public static class ComplaintBuilder {
        private Long id;
        private String title;
        private String description;
        private Location location;
        private Category category;
        private User user;
        private ComplaintProgress progress;

        public ComplaintBuilder withId(Long id) {
            this.id = id;
            return this;
        }

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

        public ComplaintBuilder withProgress(ComplaintProgress progress) {
            this.progress = progress;
            return this;
        }

        public Complaint build() {
            Complaint complaint = new Complaint(id, title, description, location, user, category, progress);
            complaint.validate();
            return complaint;
        }
    }
}
