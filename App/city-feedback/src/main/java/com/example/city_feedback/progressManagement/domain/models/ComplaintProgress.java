package com.example.city_feedback.progressManagement.domain.models;

import com.example.city_feedback.complaintManagement.domain.models.Complaint;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "complaint_progress")
public class ComplaintProgress {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    private String status;

    private String color;

    private String description;

    @OneToMany(mappedBy = "progress", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Complaint> complaints;


    public ComplaintProgress() {

    }

    public ComplaintProgress(String status, String color) {
        this.status = status;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
