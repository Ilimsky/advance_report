package org.example.reference_backend.mapper;

import org.example.reference_backend.dto.UserDto;
import org.example.reference_backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getRoles());
    }
}
