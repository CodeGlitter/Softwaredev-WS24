package com.example.city_feedback.complaintManagement.infrastructure.repositories;

import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Location entities.
 * Provides methods for accessing and managing Location data.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    /**
     * Finds a location by its street, house number, postal code, and city.
     *
     * @param street      the street name
     * @param houseNumber the house number
     * @param postalCode  the postal code
     * @param city        the city
     * @return an optional Location matching the provided address details
     */
    Optional<Location> findByStreetAndHouseNumberAndPostalCodeAndCity(
            String street, String houseNumber, String postalCode, String city);
}
