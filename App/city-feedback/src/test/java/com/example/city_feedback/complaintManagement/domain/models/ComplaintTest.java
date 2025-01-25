package com.example.city_feedback.complaintManagement.domain.models;

import com.example.city_feedback.authentication.domain.models.User;
import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Complaint} class, including edge cases and best practices.
 */
public class ComplaintTest {

    @Test
    void whenAllFieldsAreValid_thenComplaintIsCreatedSuccessfully() {
        Complaint complaint = Complaint.builder()
                .withTitle("Valid Title")
                .withDescription("This is a valid description of the complaint.")
                .withLocation(new Location("Main Street", "1", "12345", "Test City"))
                .withUser(new User())
                .build();

        assertNotNull(complaint);
        assertTrue(complaint.isValid());
    }

    @Test
    void whenTitleTooShort_thenThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Complaint.builder()
                        .withTitle("Too") // Too short
                        .withDescription("Valid Description")
                        .withLocation(new Location("Main Street", "1", "12345", "Test City"))
                        .withUser(new User())
                        .build());
        assertEquals("Titel bitte 5-100 Zeichen.", exception.getMessage());
    }

    @Test
    void whenTitleTooLong_thenThrowsException() {
        String longTitle = "a".repeat(101); // Exceeds 100 characters
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Complaint.builder()
                        .withTitle(longTitle)
                        .withDescription("Valid Description")
                        .withLocation(new Location("Main Street", "1", "12345", "Test City"))
                        .withUser(new User())
                        .build());
        assertEquals("Titel bitte 5-100 Zeichen.", exception.getMessage());
    }

    @Test
    void whenDescriptionTooShort_thenThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Complaint.builder()
                        .withTitle("Valid Title")
                        .withDescription("Short") // Too short
                        .withLocation(new Location("Main Street", "1", "12345", "Test City"))
                        .withUser(new User())
                        .build());
        assertEquals("Beschreibung bitte 10-1000 Zeichen.", exception.getMessage());
    }

    @Test
    void whenDescriptionTooLong_thenThrowsException() {
        String longDescription = "a".repeat(1001); // Exceeds 1000 characters
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Complaint.builder()
                        .withTitle("Valid Title")
                        .withDescription(longDescription)
                        .withLocation(new Location("Main Street", "1", "12345", "Test City"))
                        .withUser(new User())
                        .build());
        assertEquals("Beschreibung bitte 10-1000 Zeichen.", exception.getMessage());
    }

    @Test
    void whenLocationIsInvalid_thenThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Complaint.builder()
                        .withTitle("Valid Title")
                        .withDescription("Valid Description")
                        .withLocation(null) // Invalid
                        .withUser(new User())
                        .build());
        assertEquals("Pflichtfeld und gültige Zeichen bitte.", exception.getMessage());
    }

    @Test
    void whenUserIsNull_thenThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Complaint.builder()
                        .withTitle("Valid Title")
                        .withDescription("Valid Description")
                        .withLocation(new Location("Main Street", "1", "12345", "Test City"))
                        .withUser(null) // No user
                        .build());
        assertEquals("Benutzer ist erforderlich.", exception.getMessage());
    }

    @Test
    void whenTitleHasSpecialCharacters_thenComplaintIsCreatedSuccessfully() {
        Complaint complaint = Complaint.builder()
                .withTitle("Valid Title @#!")
                .withDescription("This description has valid characters.")
                .withLocation(new Location("Main Street", "1", "12345", "Test City"))
                .withUser(new User())
                .build();

        assertNotNull(complaint);
        assertTrue(complaint.isValid());
        assertEquals("Valid Title @#!", complaint.getTitle());
    }

    @Test
    void whenDescriptionContainsSpecialCharacters_thenComplaintIsCreatedSuccessfully() {
        Complaint complaint = Complaint.builder()
                .withTitle("Valid Title")
                .withDescription("Valid description with special characters @#!.")
                .withLocation(new Location("Main Street", "1", "12345", "Test City"))
                .withUser(new User())
                .build();

        assertNotNull(complaint);
        assertTrue(complaint.isValid());
        assertEquals("Valid description with special characters @#!.", complaint.getDescription());
    }

    @Test
    void whenLocationIsEdgeCase_thenThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Location("", "", "", "")); // All fields empty

        assertEquals("Ungültige Straße.", exception.getMessage());
    }
}
