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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link ComplaintService} class.
 * Verifies the correctness of complaint creation and retrieval logic.
 */
class ComplaintServiceTest {

    @Mock
    private ComplaintRepository complaintRepository;

    @InjectMocks
    private ComplaintService complaintService;

    @Mock
    private CategoryRepository categoryRepository;

    /**
     * Initializes the test environment by setting up mocks for dependencies.
     * This method is executed before each test case.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
                "City",
                1);

        Location location = new Location("Street", "1", "12345", "City");

        Category category = new Category("Infrastruktur", "Description", 1);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        Complaint complaint = Complaint.builder()
                .withTitle("Title")
                .withDescription("Description")
                .withLocation(location)
                .withCategory(category)
                .withCategory(category)
                .build();

        when(complaintRepository.save(any(Complaint.class))).thenReturn(complaint);

        // Act
        Complaint result = complaintService.createComplaint(command);

        // Assert
        assertEquals("Title", result.getTitle());
        assertEquals("Description", result.getDescription());
        assertEquals(location, result.getLocation());
        assertEquals(category, result.getCategory());
        verify(complaintRepository).save(any(Complaint.class));
    }

    /**
     * Tests that an exception is thrown when {@code categoryId} is null.
     */
    @Test
    void whenCreateComplaintWithNullCategoryId_thenThrowsException() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand(
                "Title",
                "Description",
                "Street",
                "1",
                "12345",
                "City",
                null);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            complaintService.createComplaint(command);
        });

        assertEquals("Kategorie muss angegeben werden", exception.getMessage());
        verifyNoInteractions(complaintRepository);
    }

    /**
     * Tests that an exception is thrown when an invalid {@code categoryId} is provided.
     */
    @Test
    void whenCreateComplaintWithInvalidCategoryId_thenThrowsException() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand(
                "Title",
                "Description",
                "Street",
                "1",
                "12345",
                "City",
                999);

        when(categoryRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            complaintService.createComplaint(command);
        });

        assertEquals("Invalid category ID", exception.getMessage());
        verifyNoInteractions(complaintRepository);
    }

    /**
     * Tests that all complaints are retrieved from the repository and converted into {@link ComplaintDto} objects.
     * Verifies the correctness of the DTO transformation logic.
     */
    @Test
    void whenFindAllComplaints_thenReturnsListOfDtos() {
        // Arrange
        Location location = new Location("Street", "1", "12345", "City");
        Category category = new Category("Infrastruktur", "Description", 1);

        Complaint complaint = Complaint.builder()
                .withTitle("Test Title")
                .withDescription("Test Description")
                .withLocation(location)
                .withCategory(category)
                .build();

        when(complaintRepository.findAll()).thenReturn(List.of(complaint));

        // Act
        List<ComplaintDto> result = complaintService.findAllComplaints();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Test Title", result.get(0).getTitle());
        assertEquals("Test Description", result.get(0).getDescription());
        assertEquals(location.toString(), result.get(0).getLocation());
        assertEquals(1, result.get(0).getCategoryId());
        verify(complaintRepository).findAll();
    }
}
