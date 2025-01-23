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
 * Unit tests for the {@link ComplaintService} class.
 * Verifies the correctness of complaint creation, deletion, and retrieval logic.
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock Security Context
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContextHolder.setContext(securityContext);
    }

    /**
     * Tests that a complaint is successfully created when all input fields are valid.
     */
    @Test
    void whenAllFieldsAreValid_thenComplaintIsCreatedSuccessfully() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();
        command.setTitle("Test Complaint");
        command.setDescription("Description");
        command.setStreet("Test Street");
        command.setHouseNumber("123");
        command.setPostalCode("12345");
        command.setCity("Test City");
        command.setCategoryId(1);

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");

        Category mockCategory = Category.builder()
                .withName("Category Name")
                .withDescription("Description")
                .build();

        Location mockLocation = new Location("Test Street", "123", "12345", "Test City");

        Complaint mockComplaint = mock(Complaint.class);
        when(mockComplaint.getId()).thenReturn(1L);

        when(categoryRepository.findById(1)).thenReturn(Optional.of(mockCategory));
        when(userRepository.findByEmail(anyString())).thenReturn(mockUser);
        when(locationRepository.findByStreetAndHouseNumberAndPostalCodeAndCity(
                anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(mockLocation));
        when(complaintRepository.save(any(Complaint.class))).thenReturn(mockComplaint);

        // Act
        Complaint result = complaintService.createComplaint(command);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    /**
     * Tests that an exception is thrown when an invalid category ID is provided.
     */
    @Test
    void whenCategoryIsInvalid_thenThrowsException() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();
        command.setCategoryId(99); // Non-existent category

        when(categoryRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> complaintService.createComplaint(command));
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
