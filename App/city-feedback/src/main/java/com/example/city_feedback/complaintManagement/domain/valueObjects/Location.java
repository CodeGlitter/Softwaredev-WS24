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
        this.street = validateAndTrim(street, STREET_PATTERN, "Invalid street name.");
        this.houseNumber = validateAndTrim(houseNumber, HOUSE_NUMBER_PATTERN, "Invalid house number.");
        this.postalCode = validateAndTrim(postalCode, POSTAL_CODE_PATTERN, "Invalid postal code.");
        this.city = validateAndTrim(city, CITY_PATTERN, "Invalid city name.");
    }

    /**
     * Validates the given value against the specified pattern and trims it.
     * If the value does not match the pattern, an IllegalArgumentException is thrown with the provided error message.
     *
     * @param value the value to be validated and trimmed
     * @param pattern the pattern to validate the value against
     * @param errorMessage the error message to be used in the exception if validation fails
     * @return the trimmed value if it is valid
     * @throws IllegalArgumentException if the value does not match the pattern
     */
    private String validateAndTrim(String value, Pattern pattern, String errorMessage) {
        if (!isValidField(value, pattern)) {
            throw new IllegalArgumentException(errorMessage);
        }
        return value.trim();
    }

    private boolean isValidField(String value, Pattern pattern) {
        return value != null && pattern.matcher(value.trim()).matches();
    }

    /**
     * Validates the street name.
     *
     * @param street the street name to validate
     * @return {@code true} if the street name is valid, {@code false} otherwise
     */
    private boolean isValidStreet(String street) {
        return isValidField(street, STREET_PATTERN);
    }

    /**
     * Validates the house number.
     *
     * @param houseNumber the house number to validate
     * @return {@code true} if the house number is valid, {@code false} otherwise
     */
    private boolean isValidHouseNumber(String houseNumber) {
        return isValidField(houseNumber, HOUSE_NUMBER_PATTERN);
    }

    /**
     * Validates the postal code.
     *
     * @param postalCode the postal code to validate
     * @return {@code true} if the postal code is valid, {@code false} otherwise
     */
    private boolean isValidPostalCode(String postalCode) {
        return isValidField(postalCode, POSTAL_CODE_PATTERN);
    }

    /**
     * Validates all fields of the location object.
     *
     * @return {@code true} if all fields are valid, {@code false} otherwise
     */
    private boolean isValidCity(String city) {
        return isValidField(city, CITY_PATTERN);
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
