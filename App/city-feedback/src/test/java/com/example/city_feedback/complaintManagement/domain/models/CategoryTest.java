package com.example.city_feedback.complaintManagement.domain.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Category} class.
 */
class CategoryTest {

    @Test
    void whenValidNameIsProvided_thenCategoryIsCreated() {
        Category category = new Category("Infrastructure");
        assertEquals("Infrastructure", category.getName());
    }

    @Test
    void whenNameIsSetToValid_thenNoExceptionIsThrown() {
        Category category = new Category();
        category.setName("Environment");
        assertEquals("Environment", category.getName());
    }

    @Test
    void whenNameIsSetToNull_thenThrowsException() {
        Category category = new Category();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> category.setName(null));

        assertEquals("Category name cannot be null or empty", exception.getMessage());
    }

    @Test
    void whenNameIsSetToEmpty_thenThrowsException() {
        Category category = new Category();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> category.setName(""));

        assertEquals("Category name cannot be null or empty", exception.getMessage());
    }

    @Test
    void whenCategoryCreatedWithoutName_thenThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Category(null));

        assertEquals("Category name cannot be null or empty", exception.getMessage());
    }
}
