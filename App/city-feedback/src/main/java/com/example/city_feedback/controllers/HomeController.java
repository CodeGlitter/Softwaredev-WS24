package com.example.city_feedback.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /*
    * Start of the web application
    * */
    @GetMapping("/")
    public String home() {
        return  "index";
    }
}
