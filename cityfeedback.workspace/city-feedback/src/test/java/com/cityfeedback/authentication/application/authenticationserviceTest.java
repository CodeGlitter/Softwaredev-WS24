package com.cityfeedback.authentication.application;

import com.cityfeedback.authentication.application.dto.userregistration;
import com.cityfeedback.authentication.exceptions.invalidinput;
import com.cityfeedback.authentication.domain.models.User;
import com.cityfeedback.authentication.infrastructure.repositories.userrepo;
import com.cityfeedback.authentication.application.services.authenticationservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class authenticationserviceTest {

    @Mock
    private userrepo userrepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private authenticationservice authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should save a new user successfully")
    void save_ShouldSaveNewUser() throws invalidinput {
        // Arrange
        userregistration userregistration = new userregistration();
        userregistration.setFirstName("John");
        userregistration.setLastName("Doe");
        userregistration.setEmail("john.doe@example.com");
        userregistration.setPhone("1234567890");
        userregistration.setPassword("password234");

        // Mock password encoding and user saving
        when(passwordEncoder.encode("password234")).thenReturn("encodedPassword");
        when(userrepo.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        User savedUser = authenticationService.save(userregistration);

        // Assert
        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFirstName());
        assertEquals("Doe", savedUser.getLastName());
        assertEquals("john.doe@example.com", savedUser.getEmail());
        assertEquals("1234567890", savedUser.getPhone());
        assertEquals("encodedPassword", savedUser.getPassword());

        // Verify repository save method was called once with the expected user object
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userrepo, times(1)).save(userCaptor.capture());
        assertEquals("encodedPassword", userCaptor.getValue().getPassword());
    }

    @Test
    @DisplayName("Should throw InvalidInputException for already used email")
    void save_ShouldThrowInvalidInputException_WhenEmailAlreadyUsed() {
        userregistration userregistration = new userregistration();
        userregistration.setEmail("john.doe@example.com");

        // Mock repository to return a user when the email is checked
        when(userrepo.findByEmail("john.doe@example.com")).thenReturn(new User());

        invalidinput exception = assertThrows(invalidinput.class, () -> {
            authenticationService.save(userregistration);
        });
        assertEquals("E-Mail bereits in Gebrauch", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidInputException when email is invalid")
    void save_ShouldThrowExceptionWhenEmailInvalid() {
        userregistration userregistration = new userregistration();
        userregistration.setEmail("john.doe");

        assertThrows(invalidinput.class, () ->
                authenticationService.save(userregistration)
        );
    }

    @Test
    @DisplayName("Should throw InvalidInputException when phone number is invalid")
    void save_ShouldThrowExceptionWhenPhoneInvalid() {
        userregistration userregistration = new userregistration();
        userregistration.setEmail("john.doe@example.com");
        userregistration.setPhone("fg1245");

        assertThrows(invalidinput.class, () ->
                authenticationService.save(userregistration)
        );
    }

    @Test
    @DisplayName("Should throw InvalidInputException when password is invalid")
    void save_ShouldThrowExceptionWhenPasswordInvalid() {
        userregistration userregistration = new userregistration();
        userregistration.setEmail("john.doe@example.com");
        userregistration.setPhone("1234567890");
        userregistration.setPassword("1234");

        assertThrows(invalidinput.class, () ->
                authenticationService.save(userregistration)
        );
    }

    @Test
    @DisplayName("Should load user by username (email) successfully")
    void loadUserByUsername_ShouldReturnUserDetails() {
        // Arrange
        User user = new User("John", "Doe", "john.doe@example.com", "1234567890", "encodedPassword");
        when(userrepo.findByEmail("john.doe@example.com")).thenReturn(user);

        // Act
        UserDetails userDetails = authenticationService.loadUserByUsername("john.doe@example.com");

        // Assert
        assertNotNull(userDetails);
        assertEquals("john.doe@example.com", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty()); // No authorities assigned
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user not found by email")
    void loadUserByUsername_ShouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userrepo.findByEmail("unknown@example.com")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () ->
            authenticationService.loadUserByUsername("unknown@example.com")
        );
    }

}
