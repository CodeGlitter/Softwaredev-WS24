package com.cityfeedback.complaintmanagement.application.services;

import com.cityfeedback.complaintmanagement.application.commands.CreateComplaintCommand;
import com.cityfeedback.complaintmanagement.application.dto.ComplaintDto;
import com.cityfeedback.complaintmanagement.domain.models.Complaint;
import com.cityfeedback.complaintmanagement.domain.valueobjects.Location;
import com.cityfeedback.complaintmanagement.infrastructure.repositories.ComplaintRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link ComplaintService} class.
 * Verifies the correctness of complaint creation and retrieval logic.
 */
class ComplaintServiceTest {

    @Mock
    private ComplaintRepository complaintRepository;

    private ComplaintService complaintService;

    /**
     * Initializes the test environment by setting up mocks for dependencies.
     * This method is executed before each test case.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        complaintService = new ComplaintService(complaintRepository);
    }

    /**
     * Tests that a complaint is successfully created and saved to the repository.
     * Verifies that the correct {@link Complaint} is returned and saved.
     */
    @Test
    void whenCreateComplaint_thenReturnsComplaintAndSavesToRepository() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand(
                "Title",
                "Description",
                "Street",
                "1",
                "12345",
                "City"
        );

        Location location = new Location("Street", "1", "12345", "City");

        Complaint complaint = Complaint.builder()
                .withTitle("Title")
                .withDescription("Description")
                .withLocation(location)
                .build();

        when(complaintRepository.save(any(Complaint.class))).thenReturn(complaint);

        // Act
        Complaint result = complaintService.createComplaint(command);

        // Assert
        assertEquals("Title", result.getTitle());
        assertEquals("Description", result.getDescription());
        assertEquals(location, result.getLocation());
        verify(complaintRepository).save(any(Complaint.class));
    }

    /**
     * Tests that all complaints are retrieved from the repository and converted into {@link ComplaintDto} objects.
     * Verifies the correctness of the DTO transformation logic.
     */
    @Test
    void whenFindAllComplaints_thenReturnsListOfDtos() {
        // Arrange
        Location location = new Location("Street", "1", "12345", "City");
        Complaint complaint = Complaint.builder()
                .withTitle("Test Title")
                .withDescription("Test Description")
                .withLocation(location)
                .build();

        when(complaintRepository.findAll()).thenReturn(List.of(complaint));

        // Act
        List<ComplaintDto> result = complaintService.findAllComplaints();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Test Title", result.get(0).getTitle());
        assertEquals("Test Description", result.get(0).getDescription());
        assertEquals(location.toString(), result.get(0).getLocation());
        verify(complaintRepository).findAll();
    }
}
