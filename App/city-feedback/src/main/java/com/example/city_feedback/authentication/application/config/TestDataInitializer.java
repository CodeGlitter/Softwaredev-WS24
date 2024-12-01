package com.example.city_feedback.authentication.application.config;

import com.example.city_feedback.authentication.domain.models.User;
import com.example.city_feedback.authentication.infrastructure.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class TestDataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TestDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner addTestUser() {
        return args -> {
            if (userRepository.findByEmail("testuser@example.com") == null) { // Check if user already exists
                User testUser = new User(
                        "Test",                     // firstName
                        "User",                     // lastName
                        "testuser@example.com",     // email
                        "+1234567890",              // phone
                        passwordEncoder.encode("password123") // password
                );
                userRepository.save(testUser);
                System.out.println("Test user created: testuser@example.com / password123");
            }
        };
    }
}
