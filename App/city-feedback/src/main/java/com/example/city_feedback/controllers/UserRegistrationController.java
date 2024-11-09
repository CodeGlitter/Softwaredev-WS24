package com.example.city_feedback.controllers;

import com.example.city_feedback.DTO.UserRegistrationDto;
import com.example.city_feedback.Exceptions.InvalidInputException;
import com.example.city_feedback.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign-up")
public class UserRegistrationController {
    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "sign-up";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userRegistrationDto, Model model) throws InvalidInputException {
        try {
            userService.save(userRegistrationDto);
            return "redirect:/sign-up?success";
        } catch (InvalidInputException e) {
            model.addAttribute("error", e.getMessage());
            return "sign-up";
        }
    }

}
