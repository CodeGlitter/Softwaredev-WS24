package com.example.city_feedback.authentication.domain.models;

import com.example.city_feedback.complaintManagement.domain.models.Complaint;

public class Citizen extends User {
    private Complaint[] complaints;
}
