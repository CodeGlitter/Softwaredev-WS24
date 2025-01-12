package com.example.city_feedback.complaintManagement.application.services;

import com.example.city_feedback.complaintManagement.application.commands.CreateComplaintCommand;
import com.example.city_feedback.complaintManagement.application.dto.ComplaintDto;
import com.example.city_feedback.complaintManagement.domain.models.Category;
import com.example.city_feedback.complaintManagement.domain.models.Complaint;
import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.ComplaintRepository;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link ComplaintService} class.
 * Validates creation, retrieval, and error handling logic for complaints.
 */
class ComplaintServiceTest {

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ComplaintService complaintService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenCreateComplaintWithValidInputs_thenReturnsComplaintAndSavesToRepository() {
        // Arrange
        Category category = new Category("Infrastruktur", "Beschwerden über Straßen, Beleuchtung, Gehwege, Brücken oder andere öffentliche Einrichtungen.", 1);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        CreateComplaintCommand command = new CreateComplaintCommand(
                "Defekte Straße",
                "Straßenbelag ist beschädigt.",
                "Hauptstraße",
                "12",
                "12345",
                "Berlin",
                1
        );

        Location location = new Location("Hauptstraße", "12", "12345", "Berlin");
        Complaint complaint = Complaint.builder()
                .withTitle(command.getTitle())
                .withDescription(command.getDescription())
                .withLocation(location)
                .withCategory(category)
                .build();

        when(complaintRepository.save(any(Complaint.class))).thenReturn(complaint);

        // Act
        Complaint result = complaintService.createComplaint(command);

        // Assert
        assertNotNull(result);
        assertEquals(command.getTitle(), result.getTitle());
        assertEquals(command.getDescription(), result.getDescription());
        assertEquals(location, result.getLocation());
        assertEquals(category, result.getCategory());
        verify(complaintRepository).save(any(Complaint.class));
    }

    @Test
    void whenCreateComplaintWithNullCategoryId_thenThrowsException() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand(
                "Defekte Straße",
                "Straßenbelag ist beschädigt.",
                "Hauptstraße",
                "12",
                "12345",
                "Berlin",
                null
        );

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            complaintService.createComplaint(command);
        });

        assertEquals("Kategorie muss angegeben werden", exception.getMessage());
        verifyNoInteractions(complaintRepository);
    }

    @Test
    void whenCreateComplaintWithInvalidCategoryId_thenThrowsException() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand(
                "Defekte Straße",
                "Straßenbelag ist beschädigt.",
                "Hauptstraße",
                "12",
                "12345",
                "Berlin",
                999
        );

        when(categoryRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            complaintService.createComplaint(command);
        });

        assertEquals("Invalid category ID", exception.getMessage());
        verifyNoInteractions(complaintRepository);
    }

    @Test
    void whenFindAllComplaints_thenReturnsListOfDtos() {
        // Arrange
        Category category = new Category("Infrastruktur", "Beschwerden über Straßen, Beleuchtung, Gehwege, Brücken oder andere öffentliche Einrichtungen.", 1);
        Location location = new Location("Hauptstraße", "12", "12345", "Berlin");

        Complaint complaint = Complaint.builder()
                .withTitle("Test Complaint")
                .withDescription("This is a test complaint.")
                .withCategory(category)
                .withLocation(location)
                .build();

        when(complaintRepository.findAll()).thenReturn(List.of(complaint));

        // Act
        List<ComplaintDto> result = complaintService.findAllComplaints();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        ComplaintDto dto = result.get(0);
        assertEquals("Test Complaint", dto.getTitle());
        assertEquals("This is a test complaint.", dto.getDescription());
        assertEquals("Infrastruktur", dto.getCategoryName());
        assertEquals("Hauptstraße 12, 12345 Berlin", dto.getLocation());
        verify(complaintRepository).findAll();
    }
}
