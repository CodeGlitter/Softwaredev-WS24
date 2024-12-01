package com.example.city_feedback.complaintManagement.domain.valueObjects;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Location} class.
 * This class verifies the correctness of the {@link Location} value object's creation and validation logic.
 */
public class LocationTest {

    /**
     * Tests that a {@link Location} object is successfully created when all fields are valid.
     * Ensures that the constructor correctly initializes the fields and validation logic passes.
     */
    @Test
    void whenAllFieldsAreValid_thenLocationObjectIsCreated() {
        Location location = new Location("Hauptstraße", "123", "12345", "Berlin");
        assertEquals("Hauptstraße", location.getStreet());
        assertEquals("123", location.getHouseNumber());
        assertEquals("12345", location.getPostalCode());
        assertEquals("Berlin", location.getCity());
        assertTrue(location.isValid());
    }

    /**
     * Tests that an exception is thrown when any field of the {@link Location} object is invalid.
     * Ensures that the validation rules for the fields (street, house number, postal code, city) are enforced.
     */
    @Test
    void whenAnyFieldIsInvalid_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Location("", "123", "12345", "Berlin")); // Empty street
        assertThrows(IllegalArgumentException.class, () -> new Location("Hauptstraße", "", "12345", "Berlin")); // Empty house number
        assertThrows(IllegalArgumentException.class, () -> new Location("Hauptstraße", "123", "1234", "Berlin")); // Invalid PLZ
        assertThrows(IllegalArgumentException.class, () -> new Location("Hauptstraße", "123", "12345", "")); // Empty city
    }
}

