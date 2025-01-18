package com.example.city_feedback.complaintManagement.application.commands;

/**
 * Command object for creating a new complaint.
 * Encapsulates complaint title, description, and location details as flat fields.
 */
public class CreateComplaintCommand {
    private String title;
    private String description;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private Integer categoryId;


    /**
     * Constructs a new {@code CreateComplaintCommand}.
     *
     * @param title       the title of the complaint
     * @param description the description of the complaint
     * @param street      the street address of the complaint
     * @param houseNumber the house number of the complaint
     * @param postalCode  the postal code of the complaint
     * @param city        the city of the complaint
     * @param categoryId
     */
    public CreateComplaintCommand(String title, String description, String street, String houseNumber, String postalCode, String city, Integer categoryId) {
        this.title = title;
        this.description = description;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.categoryId = categoryId;
    }

    public CreateComplaintCommand() {

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}
