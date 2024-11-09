package com.example.city_feedback.repositories;

import com.example.city_feedback.domain.Complaint;
import java.util.Optional;

public interface ComplaintRepositoryInterface {
    Optional<Complaint> findById(int id);
    void save(Complaint complaint);
}
