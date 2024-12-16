package com.cityfeedback.complaintmanagement.ui.controller;

import com.cityfeedback.complaintmanagement.application.commands.CreateComplaintCommand;
import com.cityfeedback.complaintmanagement.application.services.ComplaintService;
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
    private Model model;

    private ComplaintController complaintController;

    /**
     * Sets up the test environment by initializing mocks and the {@link ComplaintController} instance.
     * This method is executed before each test case.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        complaintController = new ComplaintController(complaintService);
    }

    /**
     * Tests that the "show all complaints" method returns the correct view name and populates the model with complaint data.
     */
    @Test
    void whenShowAllComplaints_thenReturnsCorrectViewAndPopulatesModel() {
        when(complaintService.findAllComplaints()).thenReturn(new ArrayList<>());

        String view = complaintController.showAllComplaints(null, model);

        assertEquals("complaintManagement/complaints-list", view);
        verify(complaintService).findAllComplaints();
        verify(model).addAttribute(eq("complaints"), anyList());
    }

    /**
     * Tests that the "show complaint form" method returns the correct view name and populates the model with a new {@link CreateComplaintCommand}.
     */
    @Test
    void whenShowComplaintForm_thenReturnsCorrectViewAndPopulatesModel() {
        String view = complaintController.showComplaintForm(model);

        assertEquals("complaintManagement/create-complaint", view);
        verify(model).addAttribute(eq("complaint"), any(CreateComplaintCommand.class));
    }

    /**
     * Tests that the "create complaint" method redirects to the complaints list upon successful complaint creation.
     */
    @Test
    void whenCreateComplaintSucceeds_thenRedirectsToComplaintList() {
        CreateComplaintCommand command = new CreateComplaintCommand("Title", "Description", "Street", "1", "12345", "City");

        String view = complaintController.createComplaint(command, model);

        assertEquals("redirect:/complaints?success=true", view);
        verify(complaintService).createComplaint(command);
    }

    /**
     * Tests that the "create complaint" method returns to the complaint form with an error message when an exception occurs.
     */
    @Test
    void whenCreateComplaintFails_thenReturnsToComplaintFormWithError() {
        CreateComplaintCommand command = new CreateComplaintCommand("", "Description", "Street", "1", "12345", "City");
        doThrow(new IllegalArgumentException("Error creating complaint"))
                .when(complaintService).createComplaint(command);

        String view = complaintController.createComplaint(command, model);

        assertEquals("complaintManagement/create-complaint", view);
        verify(complaintService).createComplaint(command);
        verify(model).addAttribute(eq("error"), eq("Error creating complaint"));
    }
}
