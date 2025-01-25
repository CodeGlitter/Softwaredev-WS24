package com.example.city_feedback.complaintManagement.infrastructure.repositories;

import com.example.city_feedback.complaintManagement.domain.models.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Complaint entities.
 * Provides database access methods for Complaint operations.
 */
@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    /**
     * Finds complaints by the email of the associated user.
     *
     * @param email the email of the user
     * @return a list of complaints created by the user
     */
    List<Complaint> findByUser_Email(String email);

    /**
     * Checks if a complaint exists by its title.
     *
     * @param title the title of the complaint
     * @return true if a complaint with the given title exists, false otherwise
     */
    boolean existsByTitle(String title);

    List<Complaint> findByUser_Id(long l);

    List<Complaint> findByCreatorId(long attr0);
}
