package com.example.city_feedback.progressManagement.domain.models;

import java.util.Date;

public class ComplaintProgress {
    private int id;
    private int complaintId;
    private int employeeId;
    private String status;
    private String message;
    private Date lastModified;
}