package com.example.city_feedback.complaintManagement.ui.controller;
import com.example.city_feedback.complaintManagement.application.dto.CategoryDto;

import com.example.city_feedback.complaintManagement.application.commands.CreateComplaintCommand;
import com.example.city_feedback.complaintManagement.application.services.ComplaintService;
import com.example.city_feedback.complaintManagement.application.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to complaints in the City Feedback system.
 * This controller provides endpoints for viewing, creating, and managing complaints.
 */
@Controller
@RequestMapping("/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;
    private final CategoryService categoryService;

    /**
     * Constructs a new {@code ComplaintController} with the provided service.
     *
     * @param complaintService the service to handle complaint operations
     */
    public ComplaintController(ComplaintService complaintService, CategoryService categoryService) {
        this.complaintService = complaintService;
        this.categoryService = categoryService;
    }


    /**
     * Displays a list of all complaints.
     *
     * @param success optional parameter indicating the success of a previous operation
     * @param model   the model to hold data for the view
     * @return the view name for displaying the complaints list
     */
    @GetMapping
    public String showAllComplaints(@RequestParam(value = "success", required = false) String success, Model model) {
        model.addAttribute("complaints", complaintService.findAllComplaints());
        if ("true".equals(success)) {
            model.addAttribute("successMessage", "Die Beschwerde wurde erfolgreich erstellt!");
        }
        return "complaintManagement/complaints-list";
    }

    /**
     * Displays the form for creating a new complaint.
     *
     * @param model the model to hold data for the form
     * @return the view name for the complaint creation form
     */
    @GetMapping("/create-complaint")
    public String showComplaintForm(Model model) {
        model.addAttribute("complaint", new CreateComplaintCommand("", "", "", "", "", "", null));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "complaintManagement/create-complaint";
    }

    /**
     * Handles the submission of a new complaint.
     *
     * @param command the data submitted for the new complaint
     * @param model   the model to hold error messages, if any
     * @return a redirect to the complaints list on success, or the form view on error
     */
    @PostMapping
    public String createComplaint(@ModelAttribute("complaint") CreateComplaintCommand command, Model model) {
        try {
            complaintService.createComplaint(command);
            return "redirect:/complaints?success=true"; // Redirects to the complaints list on success
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "complaintManagement/create-complaint"; // Returns to the form in case of error
        }
    }

    /**
     * Returns a list of all categories.
     *
     * @return a list of {@code CategoryDto} objects
     */
    @GetMapping("/categories")
    @ResponseBody
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories(); // Use the injected CategoryService
    }
}
