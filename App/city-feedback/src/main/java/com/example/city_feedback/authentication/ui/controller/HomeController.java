package com.example.city_feedback.authentication.ui.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    /*
    * Start of the web application
    * */
    @GetMapping("/")
    public String home() {
        return  "index";
    }

    /*
     * Handler method to handle sign-in request
     * http://localhost:8080/sign-in
     * */
    @GetMapping("/sign-in")
    public String signIn(@RequestParam(value = "employee", required = false) String employee, HttpSession session) {
        if(employee != null) {
            session.setAttribute("isEmployee", employee);
        }

        return  "sign-in";
    }
}
