package com.example.city_feedback.complaintManagement.infrastructure.repositories;

import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for {@link LocationRepository}.
 */
@DataJpaTest
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    private Location testLocation;

    @BeforeEach
    void setUp() {
        testLocation = new Location("Main Street", "123", "12345", "Springfield");
        locationRepository.save(testLocation);
    }

    @Test
    void whenLocationExists_thenFindByStreetAndHouseNumberAndPostalCodeAndCityReturnsLocation() {
        // Act
        Optional<Location> foundLocation = locationRepository.findByStreetAndHouseNumberAndPostalCodeAndCity(
                "Main Street", "123", "12345", "Springfield");

        // Assert
        assertTrue(foundLocation.isPresent());
        assertEquals(testLocation.getStreet(), foundLocation.get().getStreet());
        assertEquals(testLocation.getHouseNumber(), foundLocation.get().getHouseNumber());
        assertEquals(testLocation.getPostalCode(), foundLocation.get().getPostalCode());
        assertEquals(testLocation.getCity(), foundLocation.get().getCity());
    }

    @Test
    void whenLocationDoesNotExist_thenFindByStreetAndHouseNumberAndPostalCodeAndCityReturnsEmpty() {
        // Act
        Optional<Location> foundLocation = locationRepository.findByStreetAndHouseNumberAndPostalCodeAndCity(
                "Nonexistent Street", "999", "99999", "Nowhere");

        // Assert
        assertTrue(foundLocation.isEmpty());
    }

    @Test
    void whenMultipleLocationsExist_thenFindByStreetAndHouseNumberAndPostalCodeAndCityReturnsCorrectLocation() {
        // Arrange
        Location anotherLocation = new Location("Elm Street", "456", "54321", "Shelbyville");
        locationRepository.save(anotherLocation);

        // Act
        Optional<Location> foundLocation = locationRepository.findByStreetAndHouseNumberAndPostalCodeAndCity(
                "Elm Street", "456", "54321", "Shelbyville");

        // Assert
        assertTrue(foundLocation.isPresent());
        assertEquals("Elm Street", foundLocation.get().getStreet());
        assertEquals("456", foundLocation.get().getHouseNumber());
        assertEquals("54321", foundLocation.get().getPostalCode());
        assertEquals("Shelbyville", foundLocation.get().getCity());
    }


    @Test
    void whenDeletingLocation_thenLocationIsRemoved() {
        // Act
        locationRepository.delete(testLocation);
        Optional<Location> foundLocation = locationRepository.findByStreetAndHouseNumberAndPostalCodeAndCity(
                "Main Street", "123", "12345", "Springfield");

        // Assert
        assertTrue(foundLocation.isEmpty(), "Location should be deleted");
    }
}
