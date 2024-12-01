package com.example.city_feedback.complaintManagement.infrastructure.repositories;

import com.example.city_feedback.complaintManagement.domain.models.Complaint;
import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * An in-memory implementation of the {@link ComplaintRepository} interface.
 * This repository is used for managing complaints without requiring a database.
 */

@Repository
public class InMemoryComplaintRepository implements ComplaintRepository {

    private final ConcurrentHashMap<Long, Complaint> complaints = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Constructs an {@code InMemoryComplaintRepository} with some dummy data.
     */
    public InMemoryComplaintRepository() {
        // Initialize with some dummy data
        save(Complaint.builder()
                .withTitle("Defekte Straßenlaterne")
                .withDescription("Die Straßenlaterne funktioniert nicht.")
                .withLocation(new Location("Hauptstraße", "1", "12345", "Berlin"))
                .build());
        save(Complaint.builder()
                .withTitle("Lärmproblem")
                .withDescription("Lautstärke durch Bauarbeiten stört die Nachbarschaft.")
                .withLocation(new Location("Friedrichstraße", "10", "10117", "Berlin"))
                .build());
    }

    /**
     * Saves the given complaint to the repository.
     *
     * @param complaint the complaint to save
     * @return the saved complaint
     */
    @Override
    public Complaint save(Complaint complaint) {
        if (complaint.getId() == null) {
            complaint.setId(idGenerator.getAndIncrement());
        }
        complaints.put(complaint.getId(), complaint);
        return complaint;
    }


    /**
     * Finds a complaint by its ID.
     *
     * @param id the ID of the complaint
     * @return an {@code Optional} containing the complaint if found, or empty otherwise
     */
    @Override
    public Optional<Complaint> findById(Long id) {
        return Optional.ofNullable(complaints.get(id));
    }

    /**
     * Retrieves all complaints.
     *
     * @return a list of all complaints
     */
    @Override
    public List<Complaint> findAll() {
        return new ArrayList<>(complaints.values());
    }

    /**
     * Finds complaints by title containing a given substring.
     *
     * @param title the substring to search for
     * @return a list of complaints whose titles contain the substring
     */
    @Override
    public List<Complaint> findByTitleContaining(String title) {
        return complaints.values().stream()
                .filter(c -> c.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Finds complaints by city.
     *
     * @param city the city to search for
     * @return a list of complaints located in the specified city
     */
    @Override
    public List<Complaint> findByLocationCity(String city) {
        return complaints.values().stream()
                .filter(c -> c.getLocation().getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }
}
