package com.cityfeedback.complaintmanagement.ui.controller;

import com.cityfeedback.complaintmanagement.application.commands.CreateComplaintCommand;
import com.cityfeedback.complaintmanagement.application.services.ComplaintService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Handles web requests related to complaints in the City Feedback system.
 * This controller provides endpoints for viewing, creating, and managing complaints.
 */
@Controller
@RequestMapping("/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    /**
     * Constructs a new {@code ComplaintController} with the provided service.
     *
     * @param complaintService the service to handle complaint operations
     */
    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
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
        model.addAttribute("complaint", new CreateComplaintCommand("", "", "", "", "", ""));
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
}
