package com.example.city_feedback;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ComplaintValidationTest {

    @Test
    void whenComplaintTitleIsValid_thenValidationSucceeds() {
        ComplaintValidator validator = new ComplaintValidator();
        assertTrue(validator.isValidTitle("Defekte Straßenlaterne in der Hauptstraße"));
        assertTrue(validator.isValidTitle("Müll neben Container"));
    }

    @Test
    void whenComplaintTitleIsInvalid_thenValidationFails() {
        ComplaintValidator validator = new ComplaintValidator();
        assertFalse(validator.isValidTitle(""));  // empty
        assertFalse(validator.isValidTitle("   ")); // only whitespace
        assertFalse(validator.isValidTitle("a")); // too short
    }

    @Test
    void whenLocationIsValid_thenValidationSucceeds() {
        ComplaintValidator validator = new ComplaintValidator();
        assertTrue(validator.isValidLocation("Hauptstraße 123, 12345 Berlin"));
        assertTrue(validator.isValidLocation("Marktplatz 1"));
    }

    @Test
    void whenDescriptionIsValid_thenValidationSucceeds() {
        ComplaintValidator validator = new ComplaintValidator();
        assertTrue(validator.isValidDescription("Die Straßenlaterne an der Ecke Hauptstraße/Kirchweg ist seit drei Tagen defekt."));
    }
}