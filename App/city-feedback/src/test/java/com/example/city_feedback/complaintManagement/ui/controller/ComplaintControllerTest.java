package com.example.city_feedback.complaintManagement.ui.controller;

import com.example.city_feedback.complaintManagement.application.commands.CreateComplaintCommand;
import com.example.city_feedback.complaintManagement.application.services.ComplaintService;
import com.example.city_feedback.complaintManagement.application.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link ComplaintController} class.
 * Verifies the behavior of controller methods responsible for handling complaint-related requests.
 */
class ComplaintControllerTest {

    @Mock
    private ComplaintService complaintService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private Model model;

    private ComplaintController complaintController;

    /**
     * Sets up the test environment by initializing mocks and the {@link ComplaintController} instance.
     * This method is executed before each test case.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        complaintController = new ComplaintController(complaintService, categoryService);
    }

    /**
     * Tests that the "show all complaints" method returns the correct view name and populates the model with complaint data.
     */
    @Test
    void whenShowAllComplaints_thenReturnsCorrectViewAndPopulatesModel() {
        when(complaintService.findAllComplaints()).thenReturn(new ArrayList<>());

        String view = complaintController.showAllComplaints(null, null, model);

        assertEquals("complaintManagement/complaints-list", view);
        verify(complaintService).findAllComplaints();
        verify(model).addAttribute(eq("complaints"), anyList());
    }

    /**
     * Tests that the "show complaint form" method returns the correct view name and populates the model with a new {@link CreateComplaintCommand}.
     */
    @Test
    void whenShowComplaintForm_thenReturnsCorrectViewAndPopulatesModel() {
        when(categoryService.getAllCategories()).thenReturn(new ArrayList<>());

        String view = complaintController.showEditComplaintForm(1L, model);

        assertEquals("complaintManagement/create-complaint", view);
        verify(model).addAttribute(eq("complaint"), any(CreateComplaintCommand.class));
        verify(model).addAttribute(eq("categories"), anyList());
    }

    /**
     * Tests that the "create complaint" method redirects to the complaints list upon successful complaint creation.
     */
    @Test
    void whenCreateComplaintSucceeds_thenRedirectsToComplaintList() {
        CreateComplaintCommand command = new CreateComplaintCommand(99L, "Title", "Description", "Street", "1", "12345", "City", 1);

        String view = complaintController.saveOrUpdateComplaint(99L, command, model);

        assertEquals("redirect:/complaints?success=true", view);
        verify(complaintService).createComplaint(command);
    }

    /**
     * Tests that the "create complaint" method returns to the complaint form with an error message when an exception occurs.
     */
    @Test
    void whenCreateComplaintFails_thenReturnsToComplaintFormWithError() {
        CreateComplaintCommand command = new CreateComplaintCommand(99L, "", "Description", "Street", "1", "12345", "City", 1);
        doThrow(new IllegalArgumentException("Error creating complaint"))
                .when(complaintService).createComplaint(command);

        String view = complaintController.saveOrUpdateComplaint(99L, command, model);

        assertEquals("complaintManagement/create-complaint", view);
        verify(complaintService).createComplaint(command);
        verify(model).addAttribute(eq("error"), eq("Error creating complaint"));
    }
}
