package com.example.city_feedback.authentication.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    /**
     * Bean definition for PasswordEncoder.
     * This method defines a PasswordEncoder bean that uses the BCrypt hashing algorithm.
     * The PasswordEncoder is used to encode passwords securely.
     *
     * @return a PasswordEncoder instance that uses BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
