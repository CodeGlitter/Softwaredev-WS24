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

    /**
     * Tests edge cases with maximum and minimum length fields.
     */
    @Test
    void whenFieldsAreMaxOrMinLength_thenLocationObjectIsValidated() {
        // Maximum valid length (assuming max length is 100 for all fields)
        String longString = "a".repeat(100);
        Location location = new Location(longString, "123", "12345", "Berlin");
        assertEquals(longString, location.getStreet());

        // Minimum valid length
        Location minimalLocation = new Location("A", "1", "12345", "B");
        assertEquals("A", minimalLocation.getStreet());
    }

    /**
     * Tests edge cases with invalid house numbers.
     */
    @Test
    void whenHouseNumberIsInvalid_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Location("Hauptstraße", "", "12345", "Berlin")); // Empty house number
        assertThrows(IllegalArgumentException.class, () -> new Location("Hauptstraße", "12@#", "12345", "Berlin")); // Invalid special chars
    }

    /**
     * Tests edge cases with fields containing special characters.
     */
    @Test
    void whenFieldsContainSpecialCharacters_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Location("Main-Street@#", "12B", "12345", "City@Name!"));
    }


    /**
     * Tests trimming of fields with leading or trailing spaces.
     */
    @Test
    void whenFieldsHaveSpaces_thenFieldsAreTrimmed() {
        Location location = new Location(" Hauptstraße ", " 123 ", " 12345 ", " Berlin ");
        assertEquals("Hauptstraße", location.getStreet());
        assertEquals("123", location.getHouseNumber());
        assertEquals("12345", location.getPostalCode());
        assertEquals("Berlin", location.getCity());
    }

    /**
     * Tests that two different {@link Location} objects are not equal.
     */
    @Test
    void whenTwoLocationsAreDifferent_thenHashCodeAndEqualsAreNotEqual() {
        Location location1 = new Location("Hauptstraße", "123", "12345", "Berlin");
        Location location2 = new Location("Nebenstraße", "456", "54321", "Munich");

        assertNotEquals(location1, location2);
        assertNotEquals(location1.hashCode(), location2.hashCode());
    }

    /**
     * Tests invalid postal codes.
     */
    @Test
    void whenPostalCodeIsInvalid_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Location("Hauptstraße", "123", "12AB5", "Berlin")); // Alphanumeric PLZ
        assertThrows(IllegalArgumentException.class, () -> new Location("Hauptstraße", "123", "123456", "Berlin")); // Too long
    }

    /**
     * Tests edge case with empty string fields.
     */
    @Test
    void whenFieldsAreEmptyStrings_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Location("", "", "", ""));
    }
}
