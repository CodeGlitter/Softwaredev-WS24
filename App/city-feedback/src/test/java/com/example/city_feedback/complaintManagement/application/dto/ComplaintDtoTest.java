package com.example.city_feedback.complaintManagement.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ComplaintDto} class.
 */
class ComplaintDtoTest {

    @Test
    void whenAllFieldsAreValid_thenComplaintDtoObjectIsCreated() {
        // Arrange & Act
        ComplaintDto complaintDto = new ComplaintDto(1L, "Pothole Issue", "There is a large pothole on Main Street.",
                "Main Street, 12345 City", "2023-01-01", 2, "Infrastructure");

        // Assert
        assertEquals(1L, complaintDto.getId());
        assertEquals("Pothole Issue", complaintDto.getTitle());
        assertEquals("There is a large pothole on Main Street.", complaintDto.getDescription());
        assertEquals("Main Street, 12345 City", complaintDto.getLocation());
        assertEquals("2023-01-01", complaintDto.getCreatedAt());
        assertEquals(2, complaintDto.getCategoryId());
        assertEquals("Infrastructure", complaintDto.getCategoryName());
    }

    @Test
    void whenSetTitleCalled_thenUpdatesTitle() {
        // Arrange
        ComplaintDto complaintDto = new ComplaintDto();

        // Act
        complaintDto.setTitle("Broken Light");

        // Assert
        assertEquals("Broken Light", complaintDto.getTitle());
    }

    @Test
    void whenSetDescriptionCalled_thenUpdatesDescription() {
        // Arrange
        ComplaintDto complaintDto = new ComplaintDto();

        // Act
        complaintDto.setDescription("The streetlight is not functioning.");

        // Assert
        assertEquals("The streetlight is not functioning.", complaintDto.getDescription());
    }

    @Test
    void whenSetLocationCalled_thenUpdatesLocation() {
        // Arrange
        ComplaintDto complaintDto = new ComplaintDto();

        // Act
        complaintDto.setLocation("Elm Street, 54321 City");

        // Assert
        assertEquals("Elm Street, 54321 City", complaintDto.getLocation());
    }

    @Test
    void whenSetCreatedAtCalled_thenUpdatesCreatedAt() {
        // Arrange
        ComplaintDto complaintDto = new ComplaintDto();

        // Act
        complaintDto.setCreatedAt("2023-02-15");

        // Assert
        assertEquals("2023-02-15", complaintDto.getCreatedAt());
    }

    @Test
    void whenSetCategoryIdCalled_thenUpdatesCategoryId() {
        // Arrange
        ComplaintDto complaintDto = new ComplaintDto();

        // Act
        complaintDto.setCategoryId(3);

        // Assert
        assertEquals(3, complaintDto.getCategoryId());
    }

    @Test
    void whenSetCategoryNameCalled_thenUpdatesCategoryName() {
        // Arrange
        ComplaintDto complaintDto = new ComplaintDto();

        // Act
        complaintDto.setCategoryName("Road Maintenance");

        // Assert
        assertEquals("Road Maintenance", complaintDto.getCategoryName());
    }
}

