package com.example.city_feedback.complaintManagement.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComplaintController {

    /*
    * Handler method to handle complaint request
    * http://localhost:8080/complaint
    * */
    @GetMapping("complaint")
    public String complaint() {
        return "complaint";
    }

}
