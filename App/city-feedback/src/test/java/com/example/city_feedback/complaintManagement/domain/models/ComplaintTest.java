package com.example.city_feedback.complaintManagement.domain.models;

import com.example.city_feedback.authentication.domain.models.User;
import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

}
