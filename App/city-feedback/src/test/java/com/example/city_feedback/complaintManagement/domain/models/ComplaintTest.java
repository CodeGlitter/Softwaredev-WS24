package com.example.city_feedback.complaintManagement.domain.models;
import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Complaint} class.
 * This class ensures the correct behavior of complaint creation and validation.
 */
public class ComplaintTest {

    /**
     * Tests that a complaint is successfully created when all fields are valid.
     * This ensures that the builder correctly sets fields and validates them.
     */
    @Test
    void whenAllFieldsAreValid_thenComplaintIsCreatedSuccessfully() {
        Complaint complaint = Complaint.builder()
                .withTitle("Defekte Straßenlaterne")
                .withDescription("Die Straßenlaterne im Stadtzentrum, Ecke Rewe funktioniert nicht.")
                .withLocation(new Location("Hauptstr.", "123", "12345", "Betzingen"))
                .build();

        assertTrue(complaint.isValid());
    }

    /**
     * Tests that an exception is thrown when the street contains invalid characters.
     * This ensures that the {@link Location} validation logic is functioning as expected.
     */
    @Test
    void whenStreetContainsInvalidCharacters_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Location("Hauptstraße@", "123", "12345", "Berlin"));
    }

}
