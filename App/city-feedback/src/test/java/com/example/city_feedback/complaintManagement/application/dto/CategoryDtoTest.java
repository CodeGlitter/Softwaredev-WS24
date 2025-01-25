package com.example.city_feedback.complaintManagement.application.dto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link CategoryDto} class.
 */
class CategoryDtoTest {

    @Test
    void whenAllFieldsAreValid_thenCategoryDtoObjectIsCreated() {
        // Arrange & Act
        CategoryDto categoryDto = new CategoryDto(1, "Education", "Education-related complaints");

        // Assert
        assertEquals(1, categoryDto.getId());
        assertEquals("Education", categoryDto.getName());
        assertEquals("Education-related complaints", categoryDto.getDescription());
    }

    @Test
    void whenSetNameCalled_thenUpdatesName() {
        // Arrange
        CategoryDto categoryDto = new CategoryDto();

        // Act
        categoryDto.setName("Health");

        // Assert
        assertEquals("Health", categoryDto.getName());
    }

    @Test
    void whenSetDescriptionCalled_thenUpdatesDescription() {
        // Arrange
        CategoryDto categoryDto = new CategoryDto();

        // Act
        categoryDto.setDescription("Health-related complaints");

        // Assert
        assertEquals("Health-related complaints", categoryDto.getDescription());
    }

    @Test
    void whenSetIdCalled_thenUpdatesId() {
        // Arrange
        CategoryDto categoryDto = new CategoryDto();

        // Act
        categoryDto.setId(42);

        // Assert
        assertEquals(42, categoryDto.getId());
    }
}
