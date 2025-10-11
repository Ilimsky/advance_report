package com.example.backend.auth;

import com.example.backend.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails extends User implements UserDetails {
    public CustomUserDetails(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setRoles(user.getRoles());
    }
}