package com.example.city_feedback.complaintManagement.application.services;

import com.example.city_feedback.authentication.domain.models.User;
import com.example.city_feedback.authentication.infrastructure.repositories.UserRepository;
import com.example.city_feedback.complaintManagement.application.commands.CreateComplaintCommand;
import com.example.city_feedback.complaintManagement.domain.models.Category;
import com.example.city_feedback.complaintManagement.domain.models.Complaint;
import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.CategoryRepository;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.ComplaintRepository;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Comprehensive Unit Tests for the {@link ComplaintService} class.
 * Covers all primary methods, including complaint creation, updates, and validation.
 */
public class ComplaintServiceTest {

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ComplaintService complaintService;

    private User mockUser;
    private Category mockCategory;
    private Location mockLocation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock Security Context
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test_user@example.com");
        SecurityContextHolder.setContext(securityContext);

        // Mock user
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test_user@example.com");

        // Mock category
        mockCategory = new Category();
        mockCategory.setId(1);
        mockCategory.setName("Road Issue");

        // Mock location
        mockLocation = new Location("Mock Street", "1", "12345", "City");
    }

    /**
     * Tests successful complaint creation with valid inputs.
     */
    @Test
    void whenValidCommand_thenComplaintIsCreatedSuccessfully() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();
        command.setTitle("Test Complaint");
        command.setDescription("Description");
        command.setStreet("Test Street");
        command.setHouseNumber("123");
        command.setPostalCode("12345");
        command.setCity("Test City");
        command.setCategoryId(1);

        when(userRepository.findByEmail("test_user@example.com")).thenReturn(mockUser);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(mockCategory));
        when(locationRepository.findByStreetAndHouseNumberAndPostalCodeAndCity(
                "Test Street", "123", "12345", "Test City"))
                .thenReturn(Optional.of(mockLocation));
        when(complaintRepository.save(any(Complaint.class))).thenAnswer(invocation -> {
            Complaint complaint = invocation.getArgument(0);
            complaint.setId(1L);
            return complaint;
        });

        // Act
        Complaint result = complaintService.createComplaint(command);

        // Assert
        assertNotNull(result);
        assertEquals("Test Complaint", result.getTitle());
        assertEquals("Description", result.getDescription());
        assertEquals(mockUser, result.getCreator());
        assertEquals(mockCategory, result.getCategory());
        assertEquals(mockLocation, result.getLocation());
    }

    /**
     * Tests that an exception is thrown when an invalid category ID is provided.
     */
    @Test
    void whenInvalidCategory_thenThrowsException() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();
        command.setCategoryId(99);

        when(categoryRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> complaintService.createComplaint(command));
    }

    /**
     * Tests that an exception is thrown when location data is incomplete.
     */
    @Test
    void whenIncompleteLocation_thenThrowsException() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();
        command.setTitle("Test Complaint");
        command.setStreet(null); // Missing street
        command.setCategoryId(1);

        when(userRepository.findByEmail("test_user@example.com")).thenReturn(mockUser);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(mockCategory));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> complaintService.createComplaint(command));
    }

    /**
     * Tests updating an existing complaint with valid inputs.
     */
    @Test
    void whenValidUpdateCommand_thenComplaintIsUpdatedSuccessfully() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();
        command.setTitle("Updated Complaint");
        command.setDescription("Updated Description");
        command.setStreet("Updated Street");
        command.setHouseNumber("456");
        command.setPostalCode("67890");
        command.setCity("Updated City");
        command.setCategoryId(1);

        Complaint existingComplaint = new Complaint();
        existingComplaint.setId(1L);
        existingComplaint.setTitle("Old Complaint");

        when(complaintRepository.findById(1L)).thenReturn(Optional.of(existingComplaint));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(mockCategory));
        when(locationRepository.findByStreetAndHouseNumberAndPostalCodeAndCity(
                "Updated Street", "456", "67890", "Updated City"))
                .thenReturn(Optional.of(mockLocation));
        when(complaintRepository.save(any(Complaint.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Complaint result = complaintService.updateComplaint(1L, command);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Complaint", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(mockCategory, result.getCategory());
        assertEquals(mockLocation, result.getLocation());
    }

    /**
     * Tests that an exception is thrown when updating a non-existent complaint.
     */
    @Test
    void whenUpdateNonExistentComplaint_thenThrowsException() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();
        command.setTitle("Updated Complaint");

        when(complaintRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> complaintService.updateComplaint(1L, command));
    }

    /**
     * Tests that a complaint is successfully deleted when it exists.
     */
    @Test
    void whenComplaintExists_thenDeletesSuccessfully() {
        // Arrange
        Long complaintId = 1L;
        when(complaintRepository.existsById(complaintId)).thenReturn(true);

        // Act
        complaintService.deleteComplaint(complaintId);

        // Assert
        verify(complaintRepository, times(1)).deleteById(complaintId);
    }

    /**
     * Tests that an exception is thrown when trying to delete a non-existent complaint.
     */
    @Test
    void whenComplaintDoesNotExist_thenThrowsException() {
        // Arrange
        Long complaintId = 1L;
        when(complaintRepository.existsById(complaintId)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> complaintService.deleteComplaint(complaintId));
    }
}