package com.example.city_feedback.authentication.infrastructure.repositories;

import com.example.city_feedback.authentication.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    // Query to find user by email and role
    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.email = :email AND r.name = :roleName")
    User findByEmailAndRole(String email, String roleName);

    User findByPhone(String phone);

    User findByEmailAndPassword(String email, String password);
}