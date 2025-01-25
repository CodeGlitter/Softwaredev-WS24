package com.example.city_feedback.complaintManagement.application.services;

import com.example.city_feedback.authentication.domain.models.User;
import com.example.city_feedback.authentication.infrastructure.repositories.UserRepository;
import com.example.city_feedback.complaintManagement.application.commands.CreateComplaintCommand;
import com.example.city_feedback.complaintManagement.application.dto.ComplaintDto;
import com.example.city_feedback.complaintManagement.domain.models.Category;
import com.example.city_feedback.complaintManagement.domain.models.Complaint;
import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.CategoryRepository;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.ComplaintRepository;
import com.example.city_feedback.complaintManagement.infrastructure.repositories.LocationRepository;
import com.example.city_feedback.progressManagement.domain.models.ComplaintProgress;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer for managing complaints.
 * Provides methods for creating complaints and retrieving complaint data.
 *
 * The application ensures valid categories are selected via a dropdown menu
 * populated directly from the database.
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
        Category category = findCategoryById(command.getCategoryId());
        User user = getAuthenticatedUser();
        Location location = resolveOrCreateLocation(command);
        ComplaintProgress defaultProgress = createDefaultProgress();

        Complaint complaint = Complaint.builder()
                .withTitle(command.getTitle())
                .withDescription(command.getDescription())
                .withLocation(location)
                .withCategory(category)
                .withUser(user)
                .build();
        complaint.setCreatorId(user.getId());
        complaint.setProgress(defaultProgress);

        return complaintRepository.save(complaint);
    }

    public List<ComplaintDto> findAllComplaints() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        return complaintRepository.findAll().stream()
                .map(complaint -> mapToDto(complaint, formatter))
                .collect(Collectors.toList());
    }


    public ComplaintDto findComplaintById(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Beschwerde mit ID " + id + " nicht gefunden."));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return mapToDto(complaint, formatter);
    }

    public Complaint updateComplaint(Long id, CreateComplaintCommand command) {
        Complaint existingComplaint = complaintRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Beschwerde nicht gefunden"));

        existingComplaint.setTitle(command.getTitle());
        existingComplaint.setDescription(command.getDescription());
        existingComplaint.setCategory(findCategoryById(command.getCategoryId()));
        existingComplaint.setLocation(resolveOrCreateLocation(command));

        return complaintRepository.save(existingComplaint);
    }

    public List<ComplaintDto> getComplaintsByCreatorEmail(String email) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        return complaintRepository.findByUser_Email(email).stream()
                .map(complaint -> mapToDto(complaint, formatter))
                .collect(Collectors.toList());
    }

    public void deleteComplaint(Long id) {
        if (!complaintRepository.existsById(id)) {
            throw new IllegalArgumentException("Beschwerde mit ID " + id + " nicht gefunden.");
        }
        complaintRepository.deleteById(id);
    }

    private Category findCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Kategorie ID"));
    }

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new IllegalArgumentException("Benutzer nicht gefunden: " + email));
    }

    private Location resolveOrCreateLocation(CreateComplaintCommand command) {
        return locationRepository.findByStreetAndHouseNumberAndPostalCodeAndCity(
                command.getStreet(),
                command.getHouseNumber(),
                command.getPostalCode(),
                command.getCity()
        ).orElseGet(() -> locationRepository.save(
                new Location(command.getStreet(), command.getHouseNumber(), command.getPostalCode(), command.getCity())
        ));
    }

    private ComplaintProgress createDefaultProgress() {
        ComplaintProgress progress = new ComplaintProgress();
        progress.setId(1); // Default ID for "Open"
        progress.setStatus("OFFEN");
        progress.setColor("#808080");
        progress.setDescription("Beschwerde wurde erstellt, ist aber noch nicht in Bearbeitung.");
        return progress;
    }

    private ComplaintDto mapToDto(Complaint complaint, DateTimeFormatter formatter) {
        String formattedCreatedAt = complaint.getCreatedAt() != null
                ? formatter.format(complaint.getCreatedAt())
                : "Unbekanntes Datum";

        return new ComplaintDto(
                complaint.getId(),
                complaint.getTitle(),
                complaint.getDescription(),
                complaint.getLocation() != null ? complaint.getLocation().toString() : "Unbekannter Standort",
                formattedCreatedAt,
                complaint.getCategory() != null ? complaint.getCategory().getId() : 0,
                complaint.getCategory() != null ? complaint.getCategory().getName() : "Keine Kategorie"
        );
    }
}
