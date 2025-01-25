package com.example.city_feedback.complaintManagement.domain.models;

import com.example.city_feedback.authentication.domain.models.User;
import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Category} domain model, including edge cases and validation logic.
 */
class CategoryTest {

    @Test
    void whenValidInputs_thenCategoryObjectIsCreated() {
        // Arrange & Act
        Category category = Category.builder()
                .withName("Education")
                .withDescription("Education-related complaints")
                .build();

        // Assert
        assertEquals("Education", category.getName());
        assertEquals("Education-related complaints", category.getDescription());
        assertTrue(category.getComplaints().isEmpty());
    }

    @Test
    void whenCategoryNameIsNull_thenThrowsException() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                Category.builder().withName(null).build()
        );
        assertEquals("Kategoriename kann nicht null oder leer sein.", exception.getMessage());
    }

    @Test
    void whenCategoryNameIsEmpty_thenThrowsException() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                Category.builder().withName("").build()
        );
        assertEquals("Kategoriename kann nicht null oder leer sein.", exception.getMessage());
    }

    @Test
    void whenDescriptionIsSet_thenCategoryObjectUpdatesSuccessfully() {
        // Arrange
        Category category = Category.builder()
                .withName("Health")
                .build();

        // Act
        category.setDescription("Health-related complaints");

        // Assert
        assertEquals("Health-related complaints", category.getDescription());
    }

    @Test
    void whenGetOptionalDescriptionCalled_thenReturnsCorrectValue() {
        // Arrange
        Category categoryWithDescription = Category.builder()
                .withName("Transport")
                .withDescription("Transport-related issues")
                .build();

        Category categoryWithoutDescription = Category.builder()
                .withName("Utilities")
                .build();

        // Act & Assert
        assertTrue(categoryWithDescription.getOptionalDescription().isPresent());
        assertEquals("Transport-related issues", categoryWithDescription.getOptionalDescription().get());

        assertTrue(categoryWithoutDescription.getOptionalDescription().isEmpty());
    }

    @Test
    void whenCategoryNameHasLeadingOrTrailingSpaces_thenSpacesAreTrimmed() {
        // Arrange & Act
        Category category = Category.builder()
                .withName("  Education  ")
                .withDescription("  Complaints about education  ")
                .build();

        // Assert
        assertEquals("Education", category.getName());
        assertEquals("  Complaints about education  ", category.getDescription());
    }

    @Test
    void whenAddingComplaints_thenComplaintsAreManagedCorrectly() {
        // Arrange
        Category category = Category.builder()
                .withName("Transport")
                .build();

        Complaint complaint1 = Complaint.builder()
                .withTitle("Bus delay")
                .withDescription("Buses are often late.")
                .withLocation(new Location("Main Street", "12", "12345", "City")) // Add required fields
                .withUser(new User()) // Add user if required
                .build();

        Complaint complaint2 = Complaint.builder()
                .withTitle("Train noise")
                .withDescription("Trains make a lot of noise.")
                .withLocation(new Location("Main Street", "12", "12345", "City")) // Add required fields
                .withUser(new User()) // Add user if required
                .build();

        // Act
        category.getComplaints().add(complaint1);
        category.getComplaints().add(complaint2);

        // Assert
        assertEquals(2, category.getComplaints().size());
        assertTrue(category.getComplaints().contains(complaint1));
        assertTrue(category.getComplaints().contains(complaint2));
    }


    @Test
    void whenRemovingComplaints_thenComplaintsAreManagedCorrectly() {
        // Arrange
        Category category = Category.builder()
                .withName("Transport")
                .build();

        Complaint complaint = Complaint.builder()
                .withTitle("Bus delay")
                .withDescription("Buses are often late.")
                .withLocation(new Location("Main Street", "12", "12345", "City")) // Add required fields
                .withUser(new User()) // Add user if required
                .build();

        category.getComplaints().add(complaint);

        // Act
        category.getComplaints().remove(complaint);

        // Assert
        assertEquals(0, category.getComplaints().size());
    }


    @Test
    void whenIdIsSet_thenCategoryHasId() {
        // Arrange
        Category category = Category.builder()
                .withName("Education")
                .withDescription("Education-related complaints")
                .build();

        // Act
        category.setId(100);

        // Assert
        assertEquals(100, category.getId());
    }
}