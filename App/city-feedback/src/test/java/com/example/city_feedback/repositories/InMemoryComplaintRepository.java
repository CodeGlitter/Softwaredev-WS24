package com.example.city_feedback.repositories;

import com.example.city_feedback.domain.Complaint;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryComplaintRepository implements ComplaintRepositoryInterface {

    private final Map<Integer, Complaint> complaints = new HashMap<>();
    private int nextId = 1;

    @Override
    public Optional<Complaint> findById(int id) {
        return Optional.ofNullable(complaints.get(id));
    }

    @Override
    public void save(Complaint complaint) {
        if (complaint.getId() == 0) {
            complaint.setId(nextId++);
        }
        complaints.put(complaint.getId(), complaint);
    }
}
