package org.example.usermanagementservice;

public interface UserService {
    UserDTO getUserById(Long id);
    UserDTO createUser(UserDTO userDto, String rawPassword);
    void deleteUser(Long id);
    UserDTO updateUser(Long id, UserDTO userDto, String rawPassword);
}
