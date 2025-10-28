package org.example.inventory_backend.mapper;

import org.example.inventory_backend.dto.UserDto;
import org.springframework.stereotype.Component;
import org.example.inventory_backend.model.User;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getRoles());
    }
}
