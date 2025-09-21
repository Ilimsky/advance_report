package org.example.reference_backend.service;

import org.example.reference_backend.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);
    UserDto createUser(UserDto userDto, String rawPassword);
    void deleteUser(Long id);
    UserDto updateUser(Long id, UserDto userDto, String rawPassword);
}
