package com.example.city_feedback.complaintManagement.application;

import com.example.city_feedback.complaintManagement.application.services.ComplaintValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.city_feedback.complaintManagement.domain.models.Complaint;

/**
 * Test class for ComplaintValidator.
 *
 * This class contains JUnit test cases to verify the validation rules for complaints
 * within the city feedback system. Each test targets specific validation requirements
 * for fields such as title, location, and description, ensuring that edge cases,
 * boundary conditions, and typical use cases are correctly handled.
 */
class ComplaintValidationTest {

    /**
     * Tests that valid complaint titles are correctly accepted
     * Tests various valid title formats and lengths
     */
    @Test
    void whenComplaintTitleIsValid_thenValidationSucceeds() {
        ComplaintValidator validator = new ComplaintValidator();
        // Test cases with valid titles
        assertTrue(validator.isValidTitle("Defekte Straßenlaterne in der Hauptstraße"),
                "Should accept a normal length title with German characters");
        assertTrue(validator.isValidTitle("Müll neben Container"),
                "Should accept a shorter valid title with umlauts");
        // Boundary test case for minimum length
        assertTrue(validator.isValidTitle("MüllA"), "Should accept title of minimum length");
        // Boundary test case for maximum length
        String longTitle = "A".repeat(100);
        assertTrue(validator.isValidTitle(longTitle), "Should accept title of maximum length");
    }

    /**
     * Tests that invalid complaint titles are correctly rejected.
     * Covers cases such as empty, whitespace-only, overly short, and overly long titles.
     */
    @Test
    void whenComplaintTitleIsInvalid_thenValidationFails() {
        ComplaintValidator validator = new ComplaintValidator();
        // Test various invalid cases
        assertFalse(validator.isValidTitle(""),
                "Should reject empty title");
        assertFalse(validator.isValidTitle("   "),
                "Should reject whitespace-only title");
        assertFalse(validator.isValidTitle("a"),
                "Should reject title that's too short");

        // Test for a title that exceeds maximum length
        String overlyLongTitle = "A".repeat(101);
        assertFalse(validator.isValidTitle(overlyLongTitle), "Should reject title exceeding maximum length");
    }

    /**
     * Tests that valid location formats are correctly accepted.
     * Verifies various address formats common in German locations.
     */
    @Test
    void whenLocationIsValid_thenValidationSucceeds() {
        ComplaintValidator validator = new ComplaintValidator();
        // Test cases with valid German addresses
        assertTrue(validator.isValidLocation("Hauptstraße 123, 12345 Berlin"),
                "Should accept standard German address format");
        assertTrue(validator.isValidLocation("Marktplatz 1"),
                "Should accept simple location format");
        // Test for German Umlaut
        assertTrue(validator.isValidLocation("Königsallee 50"),
                "Should accept location with umlauts");
    }

    /**
     * Tests that invalid location formats are correctly rejected.
     * Covers cases of empty, whitespace-only, and formats with unsupported characters.
     */
    @Test
    void whenLocationIsInvalid_thenValidationFails() {
        ComplaintValidator validator = new ComplaintValidator();
        assertFalse(validator.isValidLocation(""), "Should reject empty location");
        assertFalse(validator.isValidLocation("   "), "Should reject whitespace-only location");
        assertFalse(validator.isValidLocation("Hauptstraße@Berlin"), "Should reject location with unsupported characters");
    }

    /**
     * Tests that valid complaint descriptions are correctly accepted
     * Tests descriptions of various lengths and valid content
     */
    @Test
    void whenDescriptionIsValid_thenValidationSucceeds() {
        ComplaintValidator validator = new ComplaintValidator();
        // Test case with valid description
        assertTrue(validator.isValidDescription(
                        "Die Straßenlaterne an der Ecke Hauptstraße/Kirchweg ist seit drei Tagen defekt."),
                "Should accept a detailed description with proper length");
        // Boundary test case for minimum length
        assertTrue(validator.isValidDescription("A".repeat(10)), "Should accept description of minimum length");
        // Boundary test case for maximum length
        assertTrue(validator.isValidDescription("A".repeat(1000)), "Should accept description of maximum length");
    }

    /**
     * Tests that invalid complaint descriptions are correctly rejected.
     * Covers cases of empty, whitespace-only, and invalid lengths.
     */
    @Test
    void whenDescriptionIsInvalid_thenValidationFails() {
        ComplaintValidator validator = new ComplaintValidator();
        assertFalse(validator.isValidDescription(""), "Should reject empty description");
        assertFalse(validator.isValidDescription("   "), "Should reject whitespace-only description");
        // Test for a description that is too short
        assertFalse(validator.isValidDescription("Short"), "Should reject description that is too short");
        // Test for a description that exceeds maximum length
        String overlyLongDescription = "A".repeat(1001);
        assertFalse(validator.isValidDescription(overlyLongDescription), "Should reject description exceeding maximum length");
    }

    /**
     * Tests the successful creation of a valid complaint through the builder.
     * Ensures that all fields meet validation requirements.
     */
    @Test
    void whenBuildingValidComplaint_thenIsValidReturnsTrue() {
        Complaint complaint = Complaint.builder()
                .withTitle("Defekte Straßenlaterne")
                .withDescription("Die Straßenlaterne an der Ecke ist defekt")
                .withLocation("Hauptstraße 123")
                .build();

        assertTrue(complaint.isValid(), "Complaint with all valid fields should be valid");
    }

    /**
     * Tests the creation of an invalid complaint with missing description.
     * Ensures that complaints missing required fields are marked as invalid.
     */
    @Test
    void whenBuildingInvalidComplaint_thenIsValidReturnsFalse() {
        Complaint complaintWithoutDescription = Complaint.builder()
                .withTitle("Defekte Straßenlaterne")
                .withLocation("Hauptstraße 123")
                // missing description
                .build();

        assertFalse(complaintWithoutDescription.isValid(), "Complaint missing description should be invalid");
    }

}