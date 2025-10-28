package org.example.inventory_backend.controller.auth_controller;

import lombok.Data;
import org.example.inventory_backend.model.Role;

import java.util.Set;


@Data
class UserRegistrationRequest {
    private String username;
    private String password;
    private Set<Role> roles;
}