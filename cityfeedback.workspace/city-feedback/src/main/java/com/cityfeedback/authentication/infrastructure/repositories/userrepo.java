package com.cityfeedback.authentication.infrastructure.repositories;

import com.cityfeedback.authentication.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userrepo extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByPhone(String phone);

    User findByEmailAndPassword(String email, String password);
}