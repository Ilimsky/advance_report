package com.example.backend.controller.auth_controller;

import com.example.backend.model.Role;
import lombok.Data;

import java.util.Set;


@Data
class UserRegistrationRequest {
    private String username;
    private String password;
    private Set<Role> roles;
}