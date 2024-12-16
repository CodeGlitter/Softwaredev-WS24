package com.cityfeedback.authentication.application.services;

import com.cityfeedback.authentication.application.dto.userregistration;
import com.cityfeedback.authentication.exceptions.invalidinput;
import com.cityfeedback.authentication.domain.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface userservice extends UserDetailsService {
    User save(userregistration signUpDto) throws invalidinput;
}
