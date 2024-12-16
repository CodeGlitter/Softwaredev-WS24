package com.cityfeedback.authentication.ui.controller;

import com.cityfeedback.authentication.application.dto.userregistration;
import com.cityfeedback.authentication.exceptions.invalidinput;
import com.cityfeedback.authentication.application.services.userservice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign-up")
public class UserRegistrationController {
    private final userservice userService;

    public UserRegistrationController(userservice userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public userregistration userRegistrationDto() {
        return new userregistration();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "sign-up";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") userregistration userregistration, Model model) throws invalidinput {
        try {
            userService.save(userregistration);
            return "redirect:/sign-up?success";
        } catch (invalidinput e) {
            model.addAttribute("error", e.getMessage());
            return "sign-up";
        }
    }

}
