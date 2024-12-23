package com.cityfeedback.authentication.application.services;

import com.cityfeedback.authentication.application.dto.userregistration;
import com.cityfeedback.authentication.exceptions.invalidinput;
import com.cityfeedback.authentication.domain.models.User;
import com.cityfeedback.authentication.infrastructure.repositories.userrepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;


@Service
public class authenticationservice implements userservice {

    private final userrepo userrepo;
    private final PasswordEncoder  passwordEncoder;


    public authenticationservice(userrepo userrepo, PasswordEncoder  passwordEncoder) {
        this.userrepo = userrepo;
        this.passwordEncoder = passwordEncoder;
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
    public User save(userregistration signUpDto) throws invalidinput {
        this.validateEmail(signUpDto.getEmail());
        this.validatePhone(signUpDto.getPhone());
        this.validatePassword(signUpDto.getPassword());

        User user = new User(
            signUpDto.getFirstName(),
            signUpDto.getLastName(),
            signUpDto.getEmail(),
            signUpDto.getPhone(),
            passwordEncoder.encode(signUpDto.getPassword()));

        return userrepo.save(user);
    }

    private void validateEmail(String email) throws invalidinput {
        if (userrepo.findByEmail(email) != null) {
            throw new invalidinput("E-Mail bereits in Gebrauch");
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new invalidinput("Ungültige E-Mail Adresse");
        }
    }

    private void validatePhone(String phone) throws invalidinput {
        if (!phone.matches("^(\\+\\d{1,3}[- ]?)?([\\d, /-]*\\d){7,15}$")) {
            throw new invalidinput("Ungültige Telefonnummer");
        }
    }

    private void validatePassword(String password) throws invalidinput {
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$")) {
            throw new invalidinput("Ungültiges Passwort");
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
        User user = userrepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            Collections.emptyList()
        );
    }
    
}
