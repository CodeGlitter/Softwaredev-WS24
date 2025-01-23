package com.example.city_feedback.complaintManagement.ui.controller;

import com.example.city_feedback.complaintManagement.application.commands.CreateComplaintCommand;
import com.example.city_feedback.complaintManagement.application.dto.CategoryDto;
import com.example.city_feedback.complaintManagement.application.dto.ComplaintDto;
import com.example.city_feedback.complaintManagement.application.services.CategoryService;
import com.example.city_feedback.complaintManagement.application.services.ComplaintService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link ComplaintController}.
 */
class ComplaintControllerTest {

    @Mock
    private ComplaintService complaintService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    private ComplaintController complaintController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        complaintController = new ComplaintController(complaintService, categoryService);
    }

    @Test
    void whenListComplaints_thenReturnsCorrectViewAndPopulatesModel() {
        // Mock user details
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user@example.com");

        // Mock complaints
        List<ComplaintDto> mockComplaints = List.of(
                new ComplaintDto(1L, "Title", "Description", "Street 123, 12345 City", "2023-01-01", 1, "Category")
        );
        when(complaintService.getComplaintsByCreatorEmail("user@example.com")).thenReturn(mockComplaints);

        // Call controller method
        String view = complaintController.listComplaints(authentication, model);

        // Verify behavior and assertions
        assertEquals("complaintManagement/complaints-list", view);
        verify(complaintService).getComplaintsByCreatorEmail("user@example.com");
        verify(model).addAttribute(eq("complaints"), eq(mockComplaints));
    }

    @Test
    void whenGetAllCategories_thenReturnsCategoryList() {
        List<CategoryDto> mockCategories = List.of(new CategoryDto(1, "Category", "Description"));
        when(categoryService.getAllCategories()).thenReturn(mockCategories);

        List<CategoryDto> result = complaintController.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Category", result.get(0).getName());
        verify(categoryService).getAllCategories();
    }

    @Test
    void whenShowEditComplaintFormForNewComplaint_thenReturnsCorrectViewAndPopulatesModel() {
        when(categoryService.getAllCategories()).thenReturn(new ArrayList<>());

        String view = complaintController.showEditComplaintForm(null, model);

        assertEquals("complaintManagement/create-complaint", view);
        verify(model).addAttribute(eq("isEditMode"), eq(false));
        verify(model).addAttribute(eq("categories"), anyList());
    }

    @Test
    void whenShowEditComplaintFormForExistingComplaint_thenReturnsCorrectViewAndPopulatesModel() {
        ComplaintDto mockComplaint = new ComplaintDto(1L, "Title", "Description", "Street 123, 12345 City", "2023-01-01", 1, "Category");
        when(complaintService.findComplaintById(1L)).thenReturn(mockComplaint);
        when(categoryService.getAllCategories()).thenReturn(new ArrayList<>());

        String view = complaintController.showEditComplaintForm(1L, model);

        assertEquals("complaintManagement/create-complaint", view);
        verify(model).addAttribute(eq("isEditMode"), eq(true));
        verify(model).addAttribute(eq("complaintId"), eq(1L));
        verify(model).addAttribute(eq("categories"), anyList());
    }

    @Test
    void whenSaveOrUpdateComplaint_thenRedirectsToSuccess() {
        CreateComplaintCommand command = new CreateComplaintCommand();
        command.setTitle("Title");
        command.setDescription("Description");

        String view = complaintController.saveOrUpdateComplaint(null, command, model);

        assertEquals("redirect:/complaints?success=true", view);
        verify(complaintService).createComplaint(command);
    }

    @Test
    void whenDeleteComplaint_thenRedirectsToSuccess() {
        doNothing().when(complaintService).deleteComplaint(1L);

        String view = complaintController.deleteComplaint(1L);

        assertEquals("redirect:/complaints?success=true", view);
        verify(complaintService).deleteComplaint(1L);
    }
}
