package com.cityfeedback.complaintmanagement.infrastructure.repositories;

import com.cityfeedback.complaintmanagement.domain.models.Complaint;
import com.cityfeedback.complaintmanagement.domain.valueobjects.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link InMemoryComplaintRepository} class.
 * Verifies the behavior of the repository methods using an in-memory data store.
 */
class InMemoryComplaintRepositoryTest {

    private InMemoryComplaintRepository repository;

    /**
     * Initializes the repository before each test.
     * Ensures that each test starts with a clean instance of the repository.
     */
    @BeforeEach
    void setUp() {
        repository = new InMemoryComplaintRepository();
    }

    /**
     * Tests the save functionality of the repository.
     * Ensures that a complaint can be saved and subsequently retrieved by its ID.
     */
    @Test
    void whenSaveComplaint_thenFindItById() {
        Complaint complaint = Complaint.builder()
                .withTitle("Test Complaint")
                .withDescription("Test Description")
                .withLocation(new Location("Test Street", "10", "12345", "Test City"))
                .build();

        repository.save(complaint);
        assertTrue(repository.findById(complaint.getId()).isPresent());
    }

    /**
     * Tests the title-based filtering functionality of the repository.
     * Ensures that complaints can be filtered by a substring of their title.
     */
    @Test
    void whenFindByTitleContaining_thenFiltersCorrectly() {
        List<Complaint> results = repository.findByTitleContaining("Lärmproblem");
        assertEquals(1, results.size());
        assertEquals("Lärmproblem", results.get(0).getTitle());
    }
}
