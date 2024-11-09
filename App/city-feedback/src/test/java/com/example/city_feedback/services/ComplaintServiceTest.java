package com.example.city_feedback.services;

import com.example.city_feedback.domain.Complaint;
import com.example.city_feedback.repositories.ComplaintRepositoryInterface;
import com.example.city_feedback.repositories.InMemoryComplaintRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ComplaintServiceTest {

    private InMemoryComplaintRepository complaintRepository; // In-memory repository
    private ComplaintService complaintService; // Service under test

    @BeforeEach
    void setUp() {
        // Initialize the in-memory repository and inject it into the service
        complaintRepository = new InMemoryComplaintRepository();
        complaintService = new ComplaintService(complaintRepository);
    }

    @Test
    void modifyTitleInDraftMode_UpdatesTitle() {
        // Arrange: Create and save a complaint in draft mode
        Complaint complaint = new Complaint(1, "Original Title", "Description");
        complaintRepository.saveComplaint(complaint);

        // Act: Modify the title through the service
        complaintService.modifyComplaintTitle(1, "New Title");

        // Assert: Title is updated in the in-memory repository
        Optional<Complaint> updatedComplaint = complaintRepository.findComplaintById(1);
        assertTrue(updatedComplaint.isPresent());
        assertEquals("New Title", updatedComplaint.get().getTitle());
    }

    @Test
    void modifyDescriptionInDraftMode_UpdatesDescription() {
        // Arrange: Create and save a complaint in draft mode
        Complaint complaint = new Complaint(1, "Title", "Original Description");
        complaintRepository.saveComplaint(complaint);

        // Act: Modify the description through the service
        complaintService.modifyComplaintDescription(1, "New Description");

        // Assert: Description is updated in the in-memory repository
        Optional<Complaint> updatedComplaint = complaintRepository.findComplaintById(1);
        assertTrue(updatedComplaint.isPresent());
        assertEquals("New Description", updatedComplaint.get().getDescription());
    }

    @Test
    void modifyTitleInSubmittedMode_ThrowsException() {
        // Arrange: Create a complaint and set it to submitted mode, then save it
        Complaint complaint = new Complaint(1, "Title", "Description");
        complaint.submit(); // Change mode to submitted
        complaintRepository.saveComplaint(complaint);

        // Act & Assert: Attempt to modify title, expecting an IllegalStateException
        Exception exception = assertThrows(IllegalStateException.class, () ->
                complaintService.modifyComplaintTitle(1, "New Title")
        );

        assertEquals("Complaint is already submitted and cannot be modified", exception.getMessage());
    }

    @Test
    void modifyDescriptionInSubmittedMode_ThrowsException() {
        // Arrange: Create a complaint and set it to submitted mode, then save it
        Complaint complaint = new Complaint(1, "Title", "Description");
        complaint.submit(); // Change mode to submitted
        complaintRepository.saveComplaint(complaint);

        // Act & Assert: Attempt to modify description, expecting an IllegalStateException
        Exception exception = assertThrows(IllegalStateException.class, () ->
                complaintService.modifyComplaintDescription(1, "New Description")
        );

        assertEquals("Complaint is already submitted and cannot be modified", exception.getMessage());
    }
}
