package org.example.reference_backend.controller.user_controller;

import org.example.reference_backend.model.Role;

import java.util.Set;

class CreateUserRequest {
    private String username;
    private String password;
    private Set<Role> roles;
    private Long departmentId;

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
    public Long getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
