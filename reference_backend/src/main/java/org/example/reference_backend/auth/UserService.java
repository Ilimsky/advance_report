package org.example.reference_backend.auth;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto createUser(UserDto userDto, String rawPassword);
    void deleteUser(Long id);

    UserDto updateUser(Long id, UserDto userDto, String rawPassword);
}
