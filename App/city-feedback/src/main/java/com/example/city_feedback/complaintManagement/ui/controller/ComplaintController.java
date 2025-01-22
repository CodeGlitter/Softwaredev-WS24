package com.example.city_feedback.complaintManagement.ui.controller;
import com.example.city_feedback.complaintManagement.application.dto.CategoryDto;
import com.example.city_feedback.complaintManagement.application.dto.ComplaintDto;
import com.example.city_feedback.complaintManagement.domain.models.Complaint;
import com.example.city_feedback.complaintManagement.application.commands.CreateComplaintCommand;
import com.example.city_feedback.complaintManagement.application.services.ComplaintService;
import com.example.city_feedback.complaintManagement.application.services.CategoryService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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


    @GetMapping
    public String listComplaints(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        List<ComplaintDto> complaints = complaintService.getComplaintsByCreatorEmail(userEmail);
        model.addAttribute("complaints", complaints);
        return "complaintManagement/complaints-list";
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

    @GetMapping({"/create-complaint", "/{id}/edit"})
    public String showEditComplaintForm(@PathVariable(required = false) Long id, Model model) {
        CreateComplaintCommand command = new CreateComplaintCommand();

        if (id != null) {
            // Editing mode
            var complaint = complaintService.getComplaintById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Beschwerde nicht gefunden"));

            // Map the existing complaint data to the command object
            command.setTitle(complaint.getTitle());
            command.setDescription(complaint.getDescription());
            command.setCategoryId(complaint.getCategory().getId());
            command.setStreet(complaint.getLocation().getStreet());
            command.setHouseNumber(complaint.getLocation().getHouseNumber());
            command.setPostalCode(complaint.getLocation().getPostalCode());
            command.setCity(complaint.getLocation().getCity());

            model.addAttribute("isEditMode", true);
            model.addAttribute("complaintId", id);
        } else {
            // Creating mode
            command.setCategoryId(0); // Default category
            model.addAttribute("isEditMode", false);
        }

        model.addAttribute("complaint", command);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "complaintManagement/create-complaint";
    }


    @GetMapping("/{id}")
    public String getComplaintById(@PathVariable Long id, Model model) {
        Complaint complaint = complaintService.getComplaintById(id)
                .orElseThrow(() -> new IllegalArgumentException("Beschwerde nicht gefunden"));
        model.addAttribute("complaint", complaint);
        return "complaintManagement/view-complaint"; // Replace with your template name
    }

    @PostMapping({"/create-complaint", "/{id}"})
    public String saveOrUpdateComplaint(@PathVariable(required = false) Long id,
                                        @ModelAttribute("complaint") CreateComplaintCommand command,
                                        Model model) {
        try {
            if (id != null) {
                // Update the existing complaint
                complaintService.updateComplaint(id, command);
                return "redirect:/complaints?editSuccess=true";
            } else {
                // Create a new complaint
                complaintService.createComplaint(command);
                return "redirect:/complaints?success=true";
            }
        } catch (Exception e) {
            // Handle errors
            model.addAttribute("error", e.getMessage());
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("isEditMode", id != null); // Determine mode dynamically
            return "complaintManagement/create-complaint"; // Stay on the form view
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaintById(id);
        return "redirect:/complaints?success=true";
    }


}
