package com.cityfeedback.authentication.ui.controller;

import com.cityfeedback.authentication.domain.models.User;
import com.cityfeedback.authentication.infrastructure.repositories.userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * GlobalControllerAdvice is a class that provides global controller advice for the application.
 * It contains methods that are shared across multiple controllers.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    private final userrepo userrepo;

    @Autowired
    public GlobalControllerAdvice(userrepo userrepo) {
        this.userrepo = userrepo;
    }


    /**
     * Adds the current user to the model as an attribute named "currentUser".
     * This allows the current user to be accessed in all Thymeleaf templates.
     *
     * @return the current authenticated user, or null if no user is authenticated
     */
    @ModelAttribute("currentUser")
    public User currentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            return userrepo.findByEmail(authentication.getName());
        } catch (Exception e) {
            return null;
        }
    }
}
