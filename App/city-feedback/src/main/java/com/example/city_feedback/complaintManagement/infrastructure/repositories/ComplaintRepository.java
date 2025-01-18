package com.example.city_feedback.complaintManagement.infrastructure.repositories;

import com.example.city_feedback.complaintManagement.domain.models.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    /**
     * Finds complaints containing the specified title (case-insensitive).
     *
     * @param title the title to search for
     * @return a list of complaints matching the title
     */
    List<Complaint> findByTitleContainingIgnoreCase(String title);

    /**
     * Finds complaints by the city of their location (case-insensitive).
     *
     * @param city the city to search for
     * @return a list of complaints located in the specified city
     */
    List<Complaint> findByLocationCityIgnoreCase(String city);

    /**
     * Finds complaints by category ID.
     *
     * @param categoryId the ID of the category
     * @return a list of complaints belonging to the specified category
     */
    List<Complaint> findByCategoryId(Long categoryId);
}