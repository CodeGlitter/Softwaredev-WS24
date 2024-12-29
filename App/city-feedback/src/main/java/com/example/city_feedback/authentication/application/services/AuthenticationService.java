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
        this.validateEmail(signUpDto.getEmail());
        this.validatePhone(signUpDto.getPhone());
        this.validatePassword(signUpDto.getPassword());

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

    private void validateEmail(String email) throws InvalidInputException {
        if (userRepository.findByEmail(email) != null) {
            throw new InvalidInputException("E-Mail bereits in Gebrauch");
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidInputException("Ungültige E-Mail Adresse");
        }
    }

    private void validatePhone(String phone) throws InvalidInputException {
        if (!phone.matches("^(\\+\\d{1,3}[- ]?)?([\\d, /-]*\\d){7,15}$")) {
            throw new InvalidInputException("Ungültige Telefonnummer");
        }
    }

    private void validatePassword(String password) throws  InvalidInputException {
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$")) {
            throw new InvalidInputException("Ungültiges Passwort");
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
        System.out.println("loadUserByUsername isEmployee: " + isEmployee);

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

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
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
    
}
