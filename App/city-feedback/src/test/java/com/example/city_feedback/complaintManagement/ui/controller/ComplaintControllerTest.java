package com.example.city_feedback.complaintManagement.ui.controller;

import com.example.city_feedback.authentication.domain.models.User;
import com.example.city_feedback.complaintManagement.application.commands.CreateComplaintCommand;
import com.example.city_feedback.complaintManagement.application.dto.CategoryDto;
import com.example.city_feedback.complaintManagement.application.dto.ComplaintDto;
import com.example.city_feedback.complaintManagement.application.services.CategoryService;
import com.example.city_feedback.complaintManagement.application.services.ComplaintService;
import com.example.city_feedback.complaintManagement.domain.models.Category;
import com.example.city_feedback.complaintManagement.domain.models.Complaint;
import com.example.city_feedback.complaintManagement.domain.valueObjects.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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

    private MockMvc mockMvc;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    private ComplaintController complaintController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        complaintController = new ComplaintController(complaintService, categoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(complaintController).build();
    }

    @Test
    void whenCreateComplaint_thenRedirectsToSuccess() throws Exception {
        // Arrange
        CreateComplaintCommand command = new CreateComplaintCommand();
        command.setTitle("New Complaint");
        command.setDescription("New Description");

        // Mock the return value of createComplaint with all required fields
        Complaint mockComplaint = Complaint.builder()
                .withTitle("New Complaint")
                .withDescription("New Description")
                .withLocation(new Location("Test Street", "123", "12345", "Test City"))
                .withCategory(new Category(1, "Category Name", "Category Description"))
                .withUser(new User("user@example.com", "Test User"))
                .build();

        when(complaintService.createComplaint(any(CreateComplaintCommand.class))).thenReturn(mockComplaint);

        // Act & Assert
        mockMvc.perform(post("/complaints/create-complaint")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "New Complaint")
                        .param("description", "New Description"))
                .andExpect(status().is3xxRedirection()) // Check for any 3xx status
                .andExpect(redirectedUrl("/complaints?success=true")); // Verify redirect URL

        // Verify that the service method was called once
        verify(complaintService, times(1)).createComplaint(any(CreateComplaintCommand.class));
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
    void whenGetAllCategories_thenReturnsCategoryList() throws Exception {
        CategoryDto category1 = new CategoryDto(1, "Category 1", "Description 1");
        CategoryDto category2 = new CategoryDto(2, "Category 2", "Description 2");
        List<CategoryDto> categories = Arrays.asList(category1, category2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/complaints/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Category 2"));
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


    @Test
    void whenGetCategories_thenReturnsCategoryList() throws Exception {
        // Arrange
        CategoryDto category1 = new CategoryDto(1, "Category 1", "Description 1");
        CategoryDto category2 = new CategoryDto(2, "Category 2", "Description 2");
        List<CategoryDto> categories = Arrays.asList(category1, category2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        // Act & Assert
        mockMvc.perform(get("/complaints/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Category 2"));
    }


    @Test
    void whenDeleteComplaintWithInvalidId_thenReturnsNotFound() throws Exception {
        doThrow(new IllegalArgumentException("Complaint not found")).when(complaintService).deleteComplaint(anyLong());

        mockMvc.perform(delete("/complaints/delete/{id}", 999L))
                .andExpect(status().isNotFound());
    }




}
