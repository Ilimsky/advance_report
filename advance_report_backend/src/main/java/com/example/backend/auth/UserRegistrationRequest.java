package com.example.backend.auth;

import java.util.Set;

class UserRegistrationRequest {
    private String username;
    private String password;
    private Set<Role> roles;

    // геттеры и сеттеры
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}