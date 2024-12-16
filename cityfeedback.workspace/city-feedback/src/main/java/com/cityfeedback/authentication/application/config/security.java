package com.cityfeedback.authentication.application.config;

import com.cityfeedback.authentication.application.services.userservice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class security {


    private final userservice userService;
    private final PasswordEncoder passwordEncoder;

    public security(userservice userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Configures Spring Security for the application.
     * Redirect the user to the sign-in page if they are not authenticated.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return A configured SecurityFilterChain.
     * @throws Exception If an error occurs during security configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers(
                    "/",
                    "/home",
                    "/sign-up",
                    "/js/**",
                    "/css/**",
                    "/img/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/sign-in")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout((logout) -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/sign-in?logout")
                    .permitAll()
            );

        return http.build();
    }


    /**
     * Bean definition for DaoAuthenticationProvider.
     * This method defines a DaoAuthenticationProvider bean that uses the UserService and PasswordEncoder.
     * The DaoAuthenticationProvider is used to authenticate users based on the data from the UserService.
     *
     * @return a DaoAuthenticationProvider instance configured with UserService and PasswordEncoder
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    /**
     * Bean definition for AuthenticationManager.
     * This method defines an AuthenticationManager bean that uses the AuthenticationConfiguration.
     * The AuthenticationManager is used to authenticate users.
     *
     * @param authenticationConfiguration The AuthenticationConfiguration object to configure the AuthenticationManager.
     * @return an AuthenticationManager instance
     * @throws Exception If an error occurs during authentication manager configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
