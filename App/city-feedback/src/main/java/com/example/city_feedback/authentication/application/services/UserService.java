package com.example.city_feedback.authentication.application.services;

import com.example.city_feedback.authentication.application.dto.UserRegistrationDto;
import com.example.city_feedback.authentication.exceptions.InvalidInputException;
import com.example.city_feedback.authentication.domain.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto signUpDto) throws InvalidInputException;
}
