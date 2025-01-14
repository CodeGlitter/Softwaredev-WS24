package com.example.city_feedback.complaintManagement.application.services;

import com.example.city_feedback.complaintManagement.application.commands.CreateComplaintCommand;
import com.example.city_feedback.complaintManagement.application.dto.ComplaintDto;
import com.example.city_feedback.complaintManagement.domain.models.Complaint;
import com.example.city_feedback.complaintManagement.domain.models.Category;
import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.ComplaintRepository;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.CategoryRepository;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing complaints.
 * Provides methods for creating complaints and retrieving complaint data.
 */
@Service
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final CategoryRepository categoryRepository;
    private LocationRepository locationRepository;

    /**
     * Constructs a new {@code ComplaintService} with the provided repository.
     *
     * @param complaintRepository the repository to handle complaint data
     */
    public ComplaintService(ComplaintRepository complaintRepository, CategoryRepository categoryRepository) {
        this.complaintRepository = complaintRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates a new complaint based on the provided {@code CreateComplaintCommand}.
     *
     * @param command the command containing details for the new complaint
     * @return the created {@code Complaint} object
     */
    public Complaint createComplaint(CreateComplaintCommand command) {

        if (command.getCategoryId() == null || command.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Kategorie muss angegeben werden");
        }

        // Fetch or create the location
        Location location = locationRepository.findByStreetAndHouseNumberAndPostalCodeAndCity(
                command.getStreet(),
                command.getHouseNumber(),
                command.getPostalCode(),
                command.getCity()
        ).orElseGet(() -> locationRepository.save(
                new Location(command.getStreet(), command.getHouseNumber(), command.getPostalCode(), command.getCity())
        ));

        // Fetch the category
        Category category = categoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Kategorie-ID"));

        // Build and save the complaint
        Complaint complaint = Complaint.builder()
                .withTitle(command.getTitle())
                .withDescription(command.getDescription())
                .withLocation(location)
                .withCategory(category)
                .build();

        return complaintRepository.save(complaint);
    }

    /**
     * Retrieves all complaints and converts them into {@code ComplaintDto} objects
     * for simplified data representation.
     *
     * @return a list of {@code ComplaintDto} objects representing all complaints
     */
    public List<ComplaintDto> findAllComplaints() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return complaintRepository.findAll().stream()
                .map(complaint -> new ComplaintDto(
                        complaint.getTitle(),
                        complaint.getDescription(),
                        complaint.getLocation() != null ? complaint.getLocation().toString() : "Unbekannter Standort",
                        complaint.getCreatedAt() != null ? complaint.getCreatedAt().format(formatter) : "Unbekanntes Datum",
                        complaint.getCategory() != null ? complaint.getCategory().getId() : 0,
                        complaint.getCategory() != null ? complaint.getCategory().getName() : "Keine Kategorie"))
                .collect(Collectors.toList());
    }
}
