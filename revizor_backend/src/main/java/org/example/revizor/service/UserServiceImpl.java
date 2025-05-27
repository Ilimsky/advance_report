package org.example.revizor.service;

import jakarta.annotation.PostConstruct;
import org.example.revizor.model.Role;
import org.example.revizor.repository.UserRepo;
import org.example.revizor.dto.UserDto;
import org.example.revizor.model.User;
import org.example.revizor.model.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepo.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + id));
    }

    @Override
    public UserDto createUser(UserDto userDto, String rawPassword) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(rawPassword));
        return userMapper.toDto(userRepo.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new NoSuchElementException("User not found with id " + id);
        }
        userRepo.deleteById(id);
    }

    @PostConstruct
    public void initSuperAdmin() {
        if (!userRepo.findByName("superadmin").isPresent()) {
            User user = new User();
            user.setName("superadmin");
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setRoles(Set.of(Role.ROLE_SUPERADMIN));
            userRepo.save(user);
        }
    }
}
