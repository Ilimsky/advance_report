package com.example.backend.auth;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getRoles(), user.getDepartmentId());
    }

}
