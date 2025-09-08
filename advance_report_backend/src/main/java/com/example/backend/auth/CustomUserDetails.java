package com.example.backend.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails extends User implements UserDetails {

    private Long departmentId;

    public CustomUserDetails(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setRoles(user.getRoles());
        this.departmentId = user.getDepartmentId();
    }

    @Override
    public Long getDepartmentId() {
        return departmentId;
    }

}