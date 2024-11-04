package com.example.city_feedback.repositories;

import com.example.city_feedback.domain.User;

public class AuthenticationRepository {

    public  boolean tokenValidation(String token) {
        return true;
    }

    public String signIn(String email, String password, String role) {
        return null;
    }

    public int signUp(User user) {
        return 0;
    }
}
