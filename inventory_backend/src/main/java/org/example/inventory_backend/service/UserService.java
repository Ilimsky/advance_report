package org.example.inventory_backend.service;


import org.example.inventory_backend.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
}
