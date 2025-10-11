package org.example.reference_backend.service;

import org.example.reference_backend.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(Long id);
    UserDTO createUser(UserDTO userDto, String rawPassword);
    void deleteUser(Long id);
    UserDTO updateUser(Long id, UserDTO userDto, String rawPassword);
}
