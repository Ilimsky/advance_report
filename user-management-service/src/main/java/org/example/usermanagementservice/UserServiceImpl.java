package org.example.usermanagementservice;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDepartmentAssignmentServiceImpl userDepartmentBindingService;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder, UserDepartmentAssignmentServiceImpl userDepartmentBindingService) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDepartmentBindingService = userDepartmentBindingService;
    }
    @Override
    public UserDTO getUserById(Long id) {
        return userRepo.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + id));
    }
    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDto, String rawPassword) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(userDto.getRoles());
        User savedUser = userRepo.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (user.getRoles().contains(Role.ROLE_SUPERADMIN)) {
            throw new IllegalStateException("Cannot delete superadmin account");
        }
        userDepartmentBindingService.deleteBindingByUser(id);
        userRepo.deleteById(id);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDto, String rawPassword) {
        logger.info("Updating user with id={}: username={}, roles={}",
                id, userDto.getUsername(), userDto.getRoles());
        User user = userRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id={}", id);
                    return new NoSuchElementException("User not found with id " + id);
                });
        if (user.getRoles().contains(Role.ROLE_USER) && !userDto.getRoles().contains(Role.ROLE_USER)) {
            logger.info("Removing binding for userId={} as ROLE_USER was removed", id);
            userDepartmentBindingService.deleteBindingByUser(id);
        }
        if (userDto.getUsername() != null) {
            logger.debug("Updating username to {}", userDto.getUsername());
            user.setUsername(userDto.getUsername());
        }
        if (rawPassword != null && !rawPassword.isEmpty()) {
            logger.debug("Updating password for userId={}", id);
            user.setPassword(passwordEncoder.encode(rawPassword));
        }
        if (userDto.getRoles() != null) {
            logger.debug("Updating roles to {}", userDto.getRoles());
            user.setRoles(userDto.getRoles());
        }

        User updatedUser = userRepo.save(user);
        logger.info("User updated successfully: id={}", id);
        return userMapper.toDTO(updatedUser);
    }
}
