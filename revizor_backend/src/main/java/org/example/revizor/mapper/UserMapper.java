package org.example.revizor.model;

import org.example.revizor.dto.UserDto;
import org.example.revizor.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getRoles());
    }

    public User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setRoles(dto.getRoles());
        return user;
    }
}
