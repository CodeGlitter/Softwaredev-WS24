package com.example.city_feedback.complaintManagement.ui.controller;

import com.example.city_feedback.complaintManagement.application.dto.CategoryDto;
import com.example.city_feedback.complaintManagement.application.dto.ComplaintDto;
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

    @GetMapping("/categories")
    @ResponseBody
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping({"/create-complaint", "/{id}/edit"})
    public String showEditComplaintForm(@PathVariable(required = false) Long id, Model model) {
        CreateComplaintCommand command = new CreateComplaintCommand();

        if (id != null) {
            // Editing mode
            ComplaintDto complaint = complaintService.findComplaintById(id);

            command.setTitle(complaint.getTitle());
            command.setDescription(complaint.getDescription());
            command.setCategoryId(complaint.getCategoryId());

            // Handle Location as a String for command
            if (complaint.getLocation() != null) {
                String[] locationParts = complaint.getLocation().split(", ");
                if (locationParts.length == 2) {
                    String[] streetAndHouse = locationParts[0].split(" ", 2);
                    command.setStreet(streetAndHouse[0]);
                    command.setHouseNumber(streetAndHouse.length > 1 ? streetAndHouse[1] : "");
                    String[] postalAndCity = locationParts[1].split(" ", 2);
                    command.setPostalCode(postalAndCity[0]);
                    command.setCity(postalAndCity.length > 1 ? postalAndCity[1] : "");
                }
            }

            model.addAttribute("isEditMode", true);
            model.addAttribute("complaintId", id);
        } else {
            command.setCategoryId(0); // Default category
            model.addAttribute("isEditMode", false);
        }

        model.addAttribute("complaint", command);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "complaintManagement/create-complaint";
    }

    @PostMapping({"/create-complaint", "/{id}"})
    public String saveOrUpdateComplaint(@PathVariable(required = false) Long id,
                                        @ModelAttribute("complaint") CreateComplaintCommand command,
                                        Model model) {
        try {
            if (id != null) {
                complaintService.updateComplaint(id, command);
                return "redirect:/complaints?editSuccess=true";
            } else {
                complaintService.createComplaint(command);
                return "redirect:/complaints?success=true";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("isEditMode", id != null);
            return "complaintManagement/create-complaint";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return "redirect:/complaints?success=true";
    }
}
