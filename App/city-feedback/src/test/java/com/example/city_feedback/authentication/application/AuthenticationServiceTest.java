package com.example.city_feedback.authentication.application;

import com.example.city_feedback.authentication.application.dto.UserRegistrationDto;
import com.example.city_feedback.authentication.exceptions.InvalidInputException;
import com.example.city_feedback.authentication.domain.models.User;
import com.example.city_feedback.authentication.infrastructure.repositories.UserRepository;
import com.example.city_feedback.authentication.application.services.AuthenticationService;
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

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should save a new user successfully")
    void save_ShouldSaveNewUser() throws InvalidInputException {
        // Arrange
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("John");
        userRegistrationDto.setLastName("Doe");
        userRegistrationDto.setEmail("john.doe@example.com");
        userRegistrationDto.setPhone("1234567890");
        userRegistrationDto.setPassword("password234");

        // Mock password encoding and user saving
        when(passwordEncoder.encode("password234")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        User savedUser = authenticationService.save(userRegistrationDto);

        // Assert
        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFirstName());
        assertEquals("Doe", savedUser.getLastName());
        assertEquals("john.doe@example.com", savedUser.getEmail());
        assertEquals("1234567890", savedUser.getPhone());
        assertEquals("encodedPassword", savedUser.getPassword());

        // Verify repository save method was called once with the expected user object
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        assertEquals("encodedPassword", userCaptor.getValue().getPassword());
    }

    @Test
    @DisplayName("Should throw InvalidInputException for already used email")
    void save_ShouldThrowInvalidInputException_WhenEmailAlreadyUsed() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("john.doe@example.com");

        // Mock repository to return a user when the email is checked
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(new User());

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            authenticationService.save(userRegistrationDto);
        });
        assertEquals("E-Mail bereits in Gebrauch", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidInputException when email is invalid")
    void save_ShouldThrowExceptionWhenEmailInvalid() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("john.doe");

        assertThrows(InvalidInputException.class, () ->
                authenticationService.save(userRegistrationDto)
        );
    }

    @Test
    @DisplayName("Should throw InvalidInputException when phone number is invalid")
    void save_ShouldThrowExceptionWhenPhoneInvalid() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("john.doe@example.com");
        userRegistrationDto.setPhone("fg1245");

        assertThrows(InvalidInputException.class, () ->
                authenticationService.save(userRegistrationDto)
        );
    }

    @Test
    @DisplayName("Should throw InvalidInputException when password is invalid")
    void save_ShouldThrowExceptionWhenPasswordInvalid() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("john.doe@example.com");
        userRegistrationDto.setPhone("1234567890");
        userRegistrationDto.setPassword("1234");

        assertThrows(InvalidInputException.class, () ->
                authenticationService.save(userRegistrationDto)
        );
    }

    @Test
    @DisplayName("Should load user by username (email) successfully")
    void loadUserByUsername_ShouldReturnUserDetails() {
        // Arrange
        User user = new User("John", "Doe", "john.doe@example.com", "1234567890", "encodedPassword");
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(user);

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
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () ->
            authenticationService.loadUserByUsername("unknown@example.com")
        );
    }

}
