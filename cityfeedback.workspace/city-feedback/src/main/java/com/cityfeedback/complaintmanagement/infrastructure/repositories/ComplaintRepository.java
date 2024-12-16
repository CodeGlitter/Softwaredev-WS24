package com.cityfeedback.complaintmanagement.infrastructure.repositories;

import com.cityfeedback.complaintmanagement.domain.models.Complaint;

import java.util.List;
import java.util.Optional;

public interface ComplaintRepository {
    Complaint save(Complaint complaint);

    Optional<Complaint> findById(Long id);

    List<Complaint> findAll();

    List<Complaint> findByTitleContaining(String title);

    List<Complaint> findByLocationCity(String city);
}
