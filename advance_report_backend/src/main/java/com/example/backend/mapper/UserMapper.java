package com.example.backend.mapper;

import com.example.backend.dto.UserDto;
import com.example.backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getRoles());
    }
}
