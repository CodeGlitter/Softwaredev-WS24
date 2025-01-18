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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComplaintServiceTest {

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

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void whenValidCommandIsProvided_thenComplaintIsCreatedSuccessfully() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();
        command.setTitle("Test Complaint");
        command.setDescription("Test Description");
        command.setCategoryId(1);
        command.setStreet("Test Street");
        command.setHouseNumber("1");
        command.setPostalCode("12345");
        command.setCity("Test City");

        Category category = new Category();
        category.setId(1);
        category.setName("Test Category");

        Location location = new Location("Test Street", "1", "12345", "Test City");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(locationRepository.findByStreetAndHouseNumberAndPostalCodeAndCity(
                "Test Street", "1", "12345", "Test City")).thenReturn(Optional.empty());
        when(locationRepository.save(any(Location.class))).thenReturn(location);
        when(complaintRepository.save(any(Complaint.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Complaint createdComplaint = complaintService.createComplaint(command);

        // Assert
        assertNotNull(createdComplaint);
        assertEquals("Test Complaint", createdComplaint.getTitle());
        assertEquals("Test Description", createdComplaint.getDescription());
        assertEquals(category, createdComplaint.getCategory());
        assertEquals(location, createdComplaint.getLocation());
        assertEquals(user.getId(), createdComplaint.getCreatorId());
        verify(complaintRepository, times(1)).save(any(Complaint.class));
    }

    @Test
    void whenInvalidCategoryIdIsProvided_thenExceptionIsThrown() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();
        command.setCategoryId(999); // Invalid category ID

        when(categoryRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> complaintService.createComplaint(command));
        assertEquals("Ungültige Kategorie-ID", exception.getMessage());
    }

    @Test
    void whenUserNotFound_thenExceptionIsThrown() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();
        command.setCategoryId(1); // Valid category ID

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category("Valid Category", "Valid Category Description", 1)));
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> complaintService.createComplaint(command));
        assertEquals("Benutzer nicht gefunden: test@example.com", exception.getMessage());
    }
}
