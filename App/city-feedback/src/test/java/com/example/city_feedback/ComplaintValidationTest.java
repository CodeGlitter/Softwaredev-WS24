package com.example.city_feedback;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.city_feedback.domain.Complaint;

/**
 * Test class for ComplaintValidator
 * Contains test cases to verify the validation rules for complaints
 * in the city feedback system
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
    }

    /**
     * Tests that invalid complaint titles are correctly rejected
     * Checks edge cases and invalid formats
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
    }

    /**
     * Tests that valid location formats are correctly accepted
     * Tests various address formats common in German cities
     */
    @Test
    void whenLocationIsValid_thenValidationSucceeds() {
        ComplaintValidator validator = new ComplaintValidator();
        // Test cases with valid German addresses
        assertTrue(validator.isValidLocation("Hauptstraße 123, 12345 Berlin"),
                "Should accept standard German address format");
        assertTrue(validator.isValidLocation("Marktplatz 1"),
                "Should accept simple location format");
    }

    /**
     * Tests that valid complaint descriptions are correctly accepted
     * Tests descriptions of various lengths and content
     */
    @Test
    void whenDescriptionIsValid_thenValidationSucceeds() {
        ComplaintValidator validator = new ComplaintValidator();
        // Test case with valid description
        assertTrue(validator.isValidDescription(
                        "Die Straßenlaterne an der Ecke Hauptstraße/Kirchweg ist seit drei Tagen defekt."),
                "Should accept a detailed description with proper length");
    }

    @Test
    void whenBuildingValidComplaint_thenIsValidReturnsTrue() {
        Complaint complaint = Complaint.builder()
                .withTitle("Defekte Straßenlaterne")
                .withDescription("Die Straßenlaterne an der Ecke ist defekt")
                .withLocation("Hauptstraße 123")
                .build();

        assertTrue(complaint.isValid());
    }

    @Test
    void whenBuildingInvalidComplaint_thenIsValidReturnsFalse() {
        Complaint complaintWithoutDescription = Complaint.builder()
                .withTitle("Defekte Straßenlaterne")
                .withLocation("Hauptstraße 123")
                // missing description
                .build();

        assertFalse(complaintWithoutDescription.isValid());
    }

}