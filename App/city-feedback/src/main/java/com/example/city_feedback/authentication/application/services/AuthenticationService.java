package com.example.city_feedback.authentication.application.services;

import com.example.city_feedback.authentication.application.dto.UserRegistrationDto;
import com.example.city_feedback.authentication.domain.models.Role;
import com.example.city_feedback.authentication.exceptions.InvalidInputException;
import com.example.city_feedback.authentication.domain.models.User;
import com.example.city_feedback.authentication.infrastructure.repositories.RoleRepository;
import com.example.city_feedback.authentication.infrastructure.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class AuthenticationService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder  passwordEncoder;
    private final RoleRepository roleRepository;
    private final HttpSession session;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder  passwordEncoder, RoleRepository roleRepository, HttpSession session) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.session = session;
    }

    /**
     * Saves a new user to the database.
     * This method takes a UserRegistrationDto object as a parameter and uses it to create a new User object.
     * The password is encoded using the PasswordEncoder bean, and the new User object is saved to the database.
     *
     * @param signUpDto The UserRegistrationDto object containing the user's information.
     * @return The new User object that was saved to the database.
     */
    @Override
    public User save(UserRegistrationDto signUpDto) throws InvalidInputException {
        this.validateInput(signUpDto.getEmail(), List.of(
            e -> e.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")
                    ? Optional.empty()
                    : Optional.of("Ungültige E-Mail Adresse"),
            e -> userRepository.findByEmail(e) == null
                    ? Optional.empty()
                    : Optional.of("E-Mail bereits in Gebrauch")
        ));
        this.validateInput(signUpDto.getPhone(), List.of(
            e -> e.matches("^(?:\\+\\d{1,3}[- ]?)?([\\d, /-]*\\d){7,15}$|^$")
                    ? Optional.empty()
                    : Optional.of("Ungültige Telefonnummer")
        ));
        this.validateInput(signUpDto.getPassword(), List.of(
            e -> e.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$")
                    ? Optional.empty()
                    : Optional.of("Ungültiges Passwort")
        ));

        // Retrieve the existing role from the database
        Role role = roleRepository.findByName("Bürger")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        User user = new User(
            signUpDto.getFirstName(),
            signUpDto.getLastName(),
            signUpDto.getEmail(),
            signUpDto.getPhone(),
            passwordEncoder.encode(signUpDto.getPassword()),
            List.of(role));

        return userRepository.save(user);
    }

    /**
     * Validates the input against a list of rules.
     * Each rule is a function that takes a String input and returns an Optional<String> containing an error message if the rule fails.
     * If any rule fails, an InvalidInputException is thrown with the first error message.
     *
     * @param input The input string to be validated.
     * @param rules A list of functions representing the validation rules. Each function returns an Optional<String> with an error message if the rule fails.
     * @throws InvalidInputException if any rule fails, with the first error message found.
     */
    private void validateInput(String input, List<Function<String, Optional<String>>> rules) throws InvalidInputException {
        Optional<String> errorMessage = rules.stream()
                .map(rule -> rule.apply(input))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        if (errorMessage.isPresent()) {
            throw new InvalidInputException(errorMessage.get());
        }
    }

    /**
     * Loads the user by their email address.
     * This method is an implementation of the UserDetailsService interface from Spring Security.
     * It attempts to find a user in the database by their email address. If the user is not found,
     * it throws a UsernameNotFoundException. If the user is found, it returns a UserDetails object
     * containing the user's email, password, and an empty list of authorities.
     *
     * @param email The email address of the user to be loaded.
     * @return A UserDetails object containing the user's email, password, and authorities.
     * @throws UsernameNotFoundException If the user is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String isEmployee = (String) session.getAttribute("isEmployee");
        String role = "Bürger";

        if ("YES".equalsIgnoreCase(isEmployee)) {
            role = "Mitarbeiter";
        }

        User user = userRepository.findByEmailAndRole(email, role);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        session.setAttribute("currentRole", role);

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            mapRolesToAuthorities(user.getRoles())
        );
    }

    /**
     * Maps a collection of Role objects to a collection of GrantedAuthority objects.
     * This method is used to convert the roles assigned to a user into authorities
     * that can be recognized by Spring Security.
     *
     * @param roles the collection of Role objects to be mapped
     * @return a collection of GrantedAuthority objects corresponding to the roles
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    
}
