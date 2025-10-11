package org.example.usermanagementservice;

import lombok.Getter;

import java.util.Set;

@Getter
class CreateUserRequest {
    private String username;
    private String password;
    private Set<Role> roles;
    private Long departmentId;

}
