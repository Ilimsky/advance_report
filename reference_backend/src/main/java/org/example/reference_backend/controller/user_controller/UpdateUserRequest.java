package org.example.reference_backend.controller.user_controller;

import org.example.reference_backend.model.Role;

import java.util.Set;

public class UpdateUserRequest {
    private String username;
    private String password;
    private Set<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

}