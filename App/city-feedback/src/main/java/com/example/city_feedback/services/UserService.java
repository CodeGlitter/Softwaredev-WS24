package com.example.city_feedback.services;

import com.example.city_feedback.DTO.UserRegistrationDto;
import com.example.city_feedback.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto signUpDto);
}
