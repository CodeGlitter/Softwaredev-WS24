package com.example.city_feedback.complaintManagement.domain.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Category} domain model.
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
}
