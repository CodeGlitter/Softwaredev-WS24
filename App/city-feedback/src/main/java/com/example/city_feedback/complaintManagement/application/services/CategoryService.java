package com.example.city_feedback.complaintManagement.application.services;

import com.example.city_feedback.complaintManagement.application.dto.CategoryDto;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for handling category-related operations.
 */
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all categories and converts them to DTOs.
     * Validates that categories exist before returning the list.
     *
     * @return a list of CategoryDto objects
     */
    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> categories = categoryRepository.findAll().stream()
                .map(category -> new CategoryDto(
                        category.getId(),
                        category.getName(),
                        category.getDescription()))
                .collect(Collectors.toList());

        if (categories.isEmpty()) {
            throw new IllegalStateException("Keine Kategorien verfügbar");
        }

        return categories;
    }
}
