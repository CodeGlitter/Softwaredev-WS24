package com.example.city_feedback.complaintManagement.infrastructure.repositories;

import com.example.city_feedback.complaintManagement.domain.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Category entities.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Finds a category by its name.
     *
     * @param name the name of the category
     * @return an optional Category
     */
    Optional<Category> findByName(String name);

    /**
     * Checks if a category exists by its name.
     *
     * @param name the name of the category
     * @return true if the category exists, false otherwise
     */
    boolean existsByName(String name);
}
