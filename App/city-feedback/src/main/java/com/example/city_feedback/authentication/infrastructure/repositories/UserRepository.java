package com.example.city_feedback.authentication.infrastructure.repositories;

import com.example.city_feedback.authentication.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByPhone(String phone);

    User findByEmailAndPassword(String email, String password);
}