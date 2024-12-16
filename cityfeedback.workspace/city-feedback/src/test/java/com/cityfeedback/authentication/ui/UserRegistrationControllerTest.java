package com.cityfeedback.authentication.ui;

import com.cityfeedback.authentication.application.dto.userregistration;
import com.cityfeedback.authentication.ui.controller.UserRegistrationController;
import com.cityfeedback.authentication.infrastructure.repositories.userrepo;
import com.cityfeedback.authentication.application.services.userservice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserRegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock the UserService dependency
     */
    @MockBean
    private userservice userService;

    @MockBean
    private userrepo userrepo;

    @Test
    @DisplayName("Should display the registration form on GET /sign-up")
    public void showRegistrationForm_ShouldReturnSignUpForm() throws Exception {
        mockMvc.perform(get("/sign-up"))
            .andExpect(status().isOk())
            .andExpect(view().name("sign-up")) // Verify it returns the "sign-up" view
            .andExpect(model().attributeExists("user")); // Verify the model has a "user" attribute
    }

    /**
     * Test the POST /sign-up endpoint
     * Perform the POST request with form data
     * Capture and verify that userService.save was called with a UserRegistrationDto
     * Optionally, assert that the captured dto has expected values (if dto has fields to check)
     */
    @Test
    @DisplayName("Should register user and redirect on successful POST /sign-up")
    public void registerUserAccount_ShouldSaveUserAndRedirect() throws Exception {
        mockMvc.perform(post("/sign-up")
            .flashAttr("user", new userregistration()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/sign-up?success"));

        ArgumentCaptor<userregistration> captor = ArgumentCaptor.forClass(userregistration.class);
        verify(userService, times(1)).save(captor.capture());
    }
}
