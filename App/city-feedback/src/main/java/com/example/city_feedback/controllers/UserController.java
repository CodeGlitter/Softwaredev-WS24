package com.example.city_feedback.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    /*
     * Handler method to handle sign-in request
     * http://localhost:8080/sign-in
     * */
    @GetMapping("sign-in")
    public String signIn() {
        return  "sign-in";
    }

    /*
     * Handler method to handle sign-up request
     * http://localhost:8080/sign-up
     * */
    @GetMapping("sign-up")
    public String signUp() {
        return  "sign-up";
    }
}
