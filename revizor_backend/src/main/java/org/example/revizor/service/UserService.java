package org.example.revizor.service;

import org.example.revizor.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto createUser(UserDto userDto, String rawPassword);
    void deleteUser(Long id);
}
