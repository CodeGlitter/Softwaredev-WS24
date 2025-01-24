package com.example.city_feedback.complaintManagement.domain.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Collection<Complaint> complaints = new ArrayList<>();

    public Category() {
        // Default constructor for JPA
    }

    private Category(String name, String description) {
        setName(name);
        this.description = description;
    }

    public Category(Integer id, String name, String description) {
        this.id = id;
        setName(name);
        setDescription(description);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Collection<Complaint> getComplaints() {
        return complaints;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Kategoriename kann nicht null oder leer sein.");
        }
        this.name = name.trim();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Optional<String> getOptionalDescription() {
        return Optional.ofNullable(description);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static class Builder {
        private String name;
        private String description;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Category build() {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Kategoriename kann nicht null oder leer sein.");
            }
            return new Category(name, description);
        }
    }
}
