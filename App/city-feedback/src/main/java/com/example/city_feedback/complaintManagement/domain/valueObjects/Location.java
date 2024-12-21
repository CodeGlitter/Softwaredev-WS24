package com.example.city_feedback.complaintManagement.domain.valueObjects;
import com.example.city_feedback.complaintManagement.domain.models.Complaint;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.regex.Pattern;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String houseNumber;

    private String postalCode;

    private String city;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Complaint> complaints;


    // Validation patterns
    private static final Pattern STREET_PATTERN = Pattern.compile("^[A-Za-zäöüÄÖÜß\\s.]+$");
    private static final Pattern HOUSE_NUMBER_PATTERN = Pattern.compile("^[0-9]+[a-zA-Z-]*$");
    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("^[0-9]{5}$");
    private static final Pattern CITY_PATTERN = Pattern.compile("^[A-Za-zäöüÄÖÜß\\s]+$");

    public Location() {

    }

    public Location(String street, String houseNumber, String postalCode, String city) {
        if (!isValidStreet(street)) {
            throw new IllegalArgumentException("Invalid street name.");
        }
        if (!isValidHouseNumber(houseNumber)) {
            throw new IllegalArgumentException("Invalid house number.");
        }
        if (!isValidPostalCode(postalCode)) {
            throw new IllegalArgumentException("Invalid postal code.");
        }
        if (!isValidCity(city)) {
            throw new IllegalArgumentException("Invalid city name.");
        }

        this.street = street.trim();
        this.houseNumber = houseNumber.trim();
        this.postalCode = postalCode.trim();
        this.city = city.trim();
    }

    /**
     * Validates the street name.
     *
     * @param street the street name to validate
     * @return {@code true} if the street name is valid, {@code false} otherwise
     */
    private boolean isValidStreet(String street) {
        return street != null && STREET_PATTERN.matcher(street.trim()).matches();
    }

    /**
     * Validates the house number.
     *
     * @param houseNumber the house number to validate
     * @return {@code true} if the house number is valid, {@code false} otherwise
     */
    private boolean isValidHouseNumber(String houseNumber) {
        return houseNumber != null && HOUSE_NUMBER_PATTERN.matcher(houseNumber.trim()).matches();
    }

    /**
     * Validates the postal code.
     *
     * @param postalCode the postal code to validate
     * @return {@code true} if the postal code is valid, {@code false} otherwise
     */
    private boolean isValidPostalCode(String postalCode) {
        return postalCode != null && POSTAL_CODE_PATTERN.matcher(postalCode.trim()).matches();
    }

    /**
     * Validates all fields of the location object.
     *
     * @return {@code true} if all fields are valid, {@code false} otherwise
     */
    private boolean isValidCity(String city) {
        return city != null && CITY_PATTERN.matcher(city.trim()).matches();
    }

    /**
     * Validates all fields of the location object.
     *
     * @return {@code true} if all fields are valid, {@code false} otherwise
     */
    public boolean isValid() {
        return isValidStreet(street) &&
                isValidHouseNumber(houseNumber) &&
                isValidPostalCode(postalCode) &&
                isValidCity(city);
    }

    /**
     * Gets the street name.
     *
     * @return the street name
     */
    public String getStreet() {
        return street;
    }

    /**
     * Gets the house number.
     *
     * @return the house number
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Gets the postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets the city name.
     *
     * @return the city name
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns a string representation of the location in the format:
     * "street houseNumber, postalCode city".
     *
     * @return the string representation of the location
     */
    @Override
    public String toString() {
        return street + " " + houseNumber + ", " + postalCode + " " + city;
    }
}
