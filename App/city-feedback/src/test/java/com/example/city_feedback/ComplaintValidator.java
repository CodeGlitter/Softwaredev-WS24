package com.example.city_feedback;

/**
 * Validator class for complaints in the city feedback system.
 * This class handles validation of complaint data to ensure all submitted complaints
 * meet the required format and content standards.
 */
public class ComplaintValidator {

    // Minimum and maximum lengths for complaint titles
    // Title should be long enough to be meaningful but not excessively long
    private static final int MIN_TITLE_LENGTH = 5;     // e.g., "Müll"
    private static final int MAX_TITLE_LENGTH = 100;   // Reasonable limit for title length

    // Minimum and maximum lengths for complaint descriptions
    // Description needs to provide enough detail but have a reasonable size limit
    private static final int MIN_DESCRIPTION_LENGTH = 10;   // Ensures some meaningful detail is provided
    private static final int MAX_DESCRIPTION_LENGTH = 1000; // Prevents overly long descriptions

    /**
     * Validates the title of a complaint.
     *
     * @param title The complaint title to validate
     * @return true if the title is valid, false otherwise
     *
     * Validation rules:
     * - Must not be null
     * - Must not be empty or only whitespace
     * - Must be between MIN_TITLE_LENGTH and MAX_TITLE_LENGTH characters after trimming
     */
    public boolean isValidTitle(String title) {
        // First check if title exists and isn't empty
        if (title == null || title.trim().isEmpty()) {
            return false;
        }
        // Remove leading/trailing whitespace and check length
        String trimmedTitle = title.trim();
        return trimmedTitle.length() >= MIN_TITLE_LENGTH &&
                trimmedTitle.length() <= MAX_TITLE_LENGTH;
    }

    /**
     * Validates the location information of a complaint.
     *
     * @param location The location string to validate
     * @return true if the location format is valid, false otherwise
     *
     * Validation rules:
     * - Must not be null
     * - Must not be empty or only whitespace
     * - Must contain only valid characters for German addresses
     * - Accepts letters (including umlauts), numbers, spaces, and common punctuation
     */
    public boolean isValidLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return false;
        }
        // Regex explanation:
        // ^                    - Start of string
        // [A-Za-zäöüÄÖÜß\\s]  - Letters (including German umlauts), spaces
        // \\d                 - Numbers
        // .,/-               - Common address characters (dots, commas, slashes, hyphens)
        // +$                  - One or more of the above, until end of string
        return location.trim().matches("^[A-Za-zäöüÄÖÜß\\s\\d.,/-]+$");
    }

    /**
     * Validates the description of a complaint.
     *
     * @param description The complaint description to validate
     * @return true if the description is valid, false otherwise
     *
     * Validation rules:
     * - Must not be null
     * - Must not be empty or only whitespace
     * - Must be between MIN_DESCRIPTION_LENGTH and MAX_DESCRIPTION_LENGTH characters after trimming
     * - Length restrictions ensure adequate detail while preventing overly long submissions
     */
    public boolean isValidDescription(String description) {
        // Check for null or empty description
        if (description == null || description.trim().isEmpty()) {
            return false;
        }
        // Remove leading/trailing whitespace and check length
        String trimmedDesc = description.trim();
        return trimmedDesc.length() >= MIN_DESCRIPTION_LENGTH &&
                trimmedDesc.length() <= MAX_DESCRIPTION_LENGTH;
    }
}