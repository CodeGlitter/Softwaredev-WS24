package com.example.city_feedback.complaintManagement.application.services;

import com.example.city_feedback.authentication.domain.models.User;
import com.example.city_feedback.authentication.infrastructure.repositories.UserRepository;
import com.example.city_feedback.complaintManagement.application.commands.CreateComplaintCommand;
import com.example.city_feedback.complaintManagement.application.dto.ComplaintDto;
import com.example.city_feedback.complaintManagement.domain.models.Complaint;
import com.example.city_feedback.complaintManagement.domain.models.Category;
import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.ComplaintRepository;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.CategoryRepository;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.LocationRepository;
import com.example.city_feedback.progressManagement.domain.models.ComplaintProgress;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public ComplaintService(ComplaintRepository complaintRepository,
                            CategoryRepository categoryRepository,
                            LocationRepository locationRepository,
                            UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    public Complaint createComplaint(CreateComplaintCommand command) {
        // Validate the category first
        Category category = categoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Kategorie-ID"));

        // Fetch the logged-in user's email from the authentication context
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Resolve the User object based on the email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Benutzer nicht gefunden: " + email);
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

        // Create a default progress object (OFFEN)
        ComplaintProgress defaultProgress = new ComplaintProgress();
        defaultProgress.setId(1); // Set the default dummy ID for "OFFEN"
        defaultProgress.setStatus("Offen");
        defaultProgress.setColor("#808080");
        defaultProgress.setDescription("Die Beschwerde wurde erstellt, aber noch nicht bearbeitet.");

        // Build and save the complaint
        Complaint complaint = Complaint.builder()
                .withTitle(command.getTitle())
                .withDescription(command.getDescription())
                .withLocation(location)
                .withCategory(category)
                .withUser(user)
                .build();
        complaint.setCreatorId(user.getId());
        complaint.setProgress(defaultProgress); // Set the default progress

        return complaintRepository.save(complaint);
    }

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
