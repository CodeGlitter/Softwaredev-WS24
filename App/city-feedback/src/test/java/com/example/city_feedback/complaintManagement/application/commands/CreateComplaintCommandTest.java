package com.example.city_feedback.complaintManagement.application.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link CreateComplaintCommand} class, covering edge cases and best practices.
 */
public class CreateComplaintCommandTest {

    /**
     * Tests the constructor and getters for valid data.
     */
    @Test
    void whenValidData_thenCommandIsCreatedSuccessfully() {
        // Arrange
        Long id = 1L;
        String title = "Test Complaint";
        String description = "This is a test complaint.";
        String street = "Test Street";
        String houseNumber = "123";
        String postalCode = "12345";
        String city = "Test City";
        Integer categoryId = 1;

        // Act
        CreateComplaintCommand command = new CreateComplaintCommand(id, title, description, street, houseNumber, postalCode, city, categoryId);

        // Assert
        assertNotNull(command);
        assertEquals(id, command.getId());
        assertEquals(title, command.getTitle());
        assertEquals(description, command.getDescription());
        assertEquals(street, command.getStreet());
        assertEquals(houseNumber, command.getHouseNumber());
        assertEquals(postalCode, command.getPostalCode());
        assertEquals(city, command.getCity());
        assertEquals(categoryId, command.getCategoryId());
    }

    /**
     * Tests that default constructor allows setting values via setters.
     */
    @Test
    void whenDefaultConstructor_thenFieldsAreSettable() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();

        // Act
        command.setId(1L);
        command.setTitle("Test Complaint");
        command.setDescription("This is a test complaint.");
        command.setStreet("Test Street");
        command.setHouseNumber("123");
        command.setPostalCode("12345");
        command.setCity("Test City");
        command.setCategoryId(1);

        // Assert
        assertEquals(1L, command.getId());
        assertEquals("Test Complaint", command.getTitle());
        assertEquals("This is a test complaint.", command.getDescription());
        assertEquals("Test Street", command.getStreet());
        assertEquals("123", command.getHouseNumber());
        assertEquals("12345", command.getPostalCode());
        assertEquals("Test City", command.getCity());
        assertEquals(1, command.getCategoryId());
    }

    /**
     * Tests invalid data (null values) to ensure proper handling.
     */
    @Test
    void whenNullValues_thenCommandIsCreatedWithNullFields() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();

        // Act & Assert
        assertNull(command.getId());
        assertNull(command.getTitle());
        assertNull(command.getDescription());
        assertNull(command.getStreet());
        assertNull(command.getHouseNumber());
        assertNull(command.getPostalCode());
        assertNull(command.getCity());
        assertNull(command.getCategoryId());
    }

    /**
     * Tests edge cases with empty strings.
     */
    @Test
    void whenEmptyStrings_thenCommandIsCreatedSuccessfully() {
        // Arrange
        Long id = 1L;
        String title = "";
        String description = "";
        String street = "";
        String houseNumber = "";
        String postalCode = "";
        String city = "";
        Integer categoryId = 1;

        // Act
        CreateComplaintCommand command = new CreateComplaintCommand(id, title, description, street, houseNumber, postalCode, city, categoryId);

        // Assert
        assertNotNull(command);
        assertEquals(id, command.getId());
        assertEquals(title, command.getTitle());
        assertEquals(description, command.getDescription());
        assertEquals(street, command.getStreet());
        assertEquals(houseNumber, command.getHouseNumber());
        assertEquals(postalCode, command.getPostalCode());
        assertEquals(city, command.getCity());
        assertEquals(categoryId, command.getCategoryId());
    }

    /**
     * Tests that fields can be updated after creation.
     */
    @Test
    void whenFieldsUpdated_thenValuesAreUpdatedCorrectly() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();

        // Act
        command.setId(2L);
        command.setTitle("Updated Title");
        command.setDescription("Updated Description");
        command.setStreet("Updated Street");
        command.setHouseNumber("456");
        command.setPostalCode("67890");
        command.setCity("Updated City");
        command.setCategoryId(2);

        // Assert
        assertEquals(2L, command.getId());
        assertEquals("Updated Title", command.getTitle());
        assertEquals("Updated Description", command.getDescription());
        assertEquals("Updated Street", command.getStreet());
        assertEquals("456", command.getHouseNumber());
        assertEquals("67890", command.getPostalCode());
        assertEquals("Updated City", command.getCity());
        assertEquals(2, command.getCategoryId());
    }

    /**
     * Tests edge cases with very long strings.
     */
    @Test
    void whenVeryLongStrings_thenCommandHandlesCorrectly() {
        // Arrange
        String longString = "a".repeat(1000);
        CreateComplaintCommand command = new CreateComplaintCommand();

        // Act
        command.setTitle(longString);
        command.setDescription(longString);
        command.setStreet(longString);
        command.setHouseNumber(longString);
        command.setPostalCode(longString);
        command.setCity(longString);

        // Assert
        assertEquals(longString, command.getTitle());
        assertEquals(longString, command.getDescription());
        assertEquals(longString, command.getStreet());
        assertEquals(longString, command.getHouseNumber());
        assertEquals(longString, command.getPostalCode());
        assertEquals(longString, command.getCity());
    }

    /**
     * Tests edge cases with invalid category ID.
     */
    @Test
    void whenNegativeCategoryId_thenAllowsCreation() {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();

        // Act
        command.setCategoryId(-1);

        // Assert
        assertEquals(-1, command.getCategoryId());
    }
}
