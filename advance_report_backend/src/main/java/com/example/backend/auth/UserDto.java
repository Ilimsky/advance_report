package com.example.backend.auth;

import java.util.Set;

public class UserDto {
    private Long id;
    private String username;
    private Set<Role> roles;

    private Long departmentId; // Добавляем поле

    public UserDto(Long id, String username, Set<Role> roles, Long departmentId) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.departmentId = departmentId;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Long getDepartmentId() {
        return departmentId;
    }
}
