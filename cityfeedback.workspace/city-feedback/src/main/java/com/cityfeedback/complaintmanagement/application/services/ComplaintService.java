package com.cityfeedback.complaintmanagement.application.services;

import com.cityfeedback.complaintmanagement.application.commands.CreateComplaintCommand;
import com.cityfeedback.complaintmanagement.application.dto.ComplaintDto;
import com.cityfeedback.complaintmanagement.domain.models.Complaint;
import com.cityfeedback.complaintmanagement.domain.valueobjects.Location;
import com.cityfeedback.complaintmanagement.infrastructure.repositories.ComplaintRepository;
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

    /**
     * Constructs a new {@code ComplaintService} with the provided repository.
     *
     * @param complaintRepository the repository to handle complaint data
     */
    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    /**
     * Creates a new complaint based on the provided {@code CreateComplaintCommand}.
     *
     * @param command the command containing details for the new complaint
     * @return the created {@code Complaint} object
     */
    public Complaint createComplaint(CreateComplaintCommand command) {
        // Convert flat fields from CreateComplaintCommand into a Location object
        Location location = new Location(
                command.getStreet(),
                command.getHouseNumber(),
                command.getPostalCode(),
                command.getCity()
        );

        Complaint complaint = Complaint.builder()
                .withTitle(command.getTitle())
                .withDescription(command.getDescription())
                .withLocation(location)
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
                        complaint.getLocation().toString(),
                        complaint.getCreatedAt().format(formatter)))
                .collect(Collectors.toList());
    }
}
