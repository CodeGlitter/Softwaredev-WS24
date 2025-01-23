package com.example.city_feedback.complaintManagement.application.services;
import com.example.city_feedback.complaintManagement.application.dto.CategoryDto;
import com.example.city_feedback.complaintManagement.domain.models.Category;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link CategoryService}.
 */
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void whenGetAllCategories_thenReturnsCategoryDtos() {
        // Arrange
        Category category = Category.builder()
                .withName("Education")
                .withDescription("Education-related complaints")
                .build();
        List<Category> categories = List.of(category);
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<CategoryDto> categoryDtos = categoryService.getAllCategories();

        // Assert
        assertEquals(1, categoryDtos.size());
        assertEquals("Education", categoryDtos.get(0).getName());
        assertEquals("Education-related complaints", categoryDtos.get(0).getDescription());
        verify(categoryRepository).findAll();
    }

    @Test
    void whenGetAllCategoriesAndNoCategoriesExist_thenThrowsException() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(List.of());

        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, categoryService::getAllCategories);
        assertEquals("Keine Kategorien verfügbar", exception.getMessage());
        verify(categoryRepository).findAll();
    }

    @Test
    void whenCategoryExistsByName_thenReturnsTrue() {
        // Arrange
        String categoryName = "Health";
        when(categoryRepository.existsByName(categoryName)).thenReturn(true);

        // Act
        boolean exists = categoryRepository.existsByName(categoryName);

        // Assert
        assertTrue(exists);
        verify(categoryRepository).existsByName(categoryName);
    }

    @Test
    void whenCategoryDoesNotExistByName_thenReturnsFalse() {
        // Arrange
        String categoryName = "NonExistent";
        when(categoryRepository.existsByName(categoryName)).thenReturn(false);

        // Act
        boolean exists = categoryRepository.existsByName(categoryName);

        // Assert
        assertFalse(exists);
        verify(categoryRepository).existsByName(categoryName);
    }

    @Test
    void whenFindCategoryByName_thenReturnsCategoryOptional() {
        // Arrange
        String categoryName = "Health";
        Category category = Category.builder()
                .withName(categoryName)
                .withDescription("Health-related complaints")
                .build();
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));

        // Act
        Optional<Category> result = categoryRepository.findByName(categoryName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Health", result.get().getName());
        assertEquals("Health-related complaints", result.get().getDescription());
        verify(categoryRepository).findByName(categoryName);
    }

    @Test
    void whenFindCategoryByNonexistentName_thenReturnsEmptyOptional() {
        // Arrange
        String categoryName = "NonExistent";
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());

        // Act
        Optional<Category> result = categoryRepository.findByName(categoryName);

        // Assert
        assertTrue(result.isEmpty());
        verify(categoryRepository).findByName(categoryName);
    }
}
