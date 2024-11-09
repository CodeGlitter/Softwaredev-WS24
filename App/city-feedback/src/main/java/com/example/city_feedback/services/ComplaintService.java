package com.example.city_feedback.services;

import com.example.city_feedback.domain.Complaint;
import com.example.city_feedback.repositories.ComplaintRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComplaintService {

    private final ComplaintRepositoryInterface complaintRepository;

    @Autowired
    public ComplaintService(ComplaintRepositoryInterface complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public void modifyComplaintTitle(int complaintId, String newTitle) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));

        if (!"draft".equals(complaint.getMode())) {
            throw new IllegalStateException("Complaint is already submitted and cannot be modified");
        }

        complaint.setTitle(newTitle);
        complaintRepository.save(complaint);
    }

    public void modifyComplaintDescription(int complaintId, String newDescription) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));

        if (!"draft".equals(complaint.getMode())) {
            throw new IllegalStateException("Complaint is already submitted and cannot be modified");
        }

        complaint.setDescription(newDescription);
        complaintRepository.save(complaint);
    }
}
