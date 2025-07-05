package org.example.reference_backend.auth;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
    @Transactional
    public UserDto createUser(UserDto userDto, String rawPassword) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(userDto.getRoles());

        User savedUser = userRepo.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (user.getRoles().contains(Role.ROLE_SUPERADMIN)) {
            throw new IllegalStateException("Cannot delete superadmin account");
        }

        if (!userRepo.existsById(id)) {
            throw new NoSuchElementException("User not found with id " + id);
        }
        userRepo.deleteById(id);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto, String rawPassword) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + id));

        // Суперадмин может редактировать всех, админ - только обычных пользователей
        if (user.getRoles().contains(Role.ROLE_SUPERADMIN)) {
            throw new IllegalStateException("Cannot edit superadmin account");
        }

        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }

        if (rawPassword != null && !rawPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        }

        if (userDto.getRoles() != null) {
            user.setRoles(userDto.getRoles());
        }

        return userMapper.toDto(userRepo.save(user));
    }
}
