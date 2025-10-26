package org.example.usermanagementservice;

import org.example.departmentservice.DepartmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final UserDepartmentAssignmentServiceImpl userDepartmentBindingService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, UserRepo userRepo, UserDepartmentAssignmentServiceImpl userDepartmentBindingService, UserMapper userMapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.userDepartmentBindingService = userDepartmentBindingService;
        this.userMapper = userMapper;
    }

    @PostMapping("/createUser")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        try {
            if (userRepo.existsByUsername(request.getUsername())) {
                return ResponseEntity.badRequest().body("Username already exists");
            }

            UserDTO userDto = new UserDTO(null, request.getUsername(), request.getRoles());
            UserDTO createdUser = userService.createUser(userDto, request.getPassword());

            // Автоматическая привязка к филиалу
            if (request.getDepartmentId() != null && request.getRoles().contains(Role.ROLE_USER)) {
                createUserDepartmentBinding(createdUser, request.getDepartmentId());
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating user: " + e.getMessage());
        }
    }

    private void createUserDepartmentBinding(UserDTO user, Long departmentId) {
        UserDepartmentAssignmentDTO bindingDTO = new UserDepartmentAssignmentDTO();
        bindingDTO.setUser(user);

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(departmentId);
        bindingDTO.setDepartment(departmentDTO);

        userDepartmentBindingService.createBinding(bindingDTO);
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map(this::toDtoWithDepartment)
                .collect(Collectors.toList());
    }
    private UserDTO toDtoWithDepartment(User user) {
        UserDTO dto = userMapper.toDTO(user);
        return dto;
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String currentUser = auth != null ? auth.getName() : "unknown";
            String roles = auth != null ? auth.getAuthorities().toString() : "none";
            logger.info("Attempt to update user with ID: {} by user: {} with roles: {}", id, currentUser, roles);
            logger.debug("Update request: username={}, roles={}, hasPassword={}",
                    request.getUsername(), request.getRoles(), request.getPassword() != null);

            if (request.getRoles() == null) {
                logger.error("Roles cannot be null for userId={}", id);
                throw new IllegalArgumentException("Roles cannot be null");
            }
            Set<Role> rolesSet = request.getRoles().stream()
                    .map(roleStr -> {
                        try {
                            return Role.valueOf(String.valueOf(roleStr));
                        } catch (IllegalArgumentException e) {
                            logger.error("Invalid role: {}", roleStr);
                            throw new IllegalArgumentException("Invalid role: " + roleStr);
                        }
                    })
                    .collect(Collectors.toSet());

            UserDTO userDto = new UserDTO(id, request.getUsername(), rolesSet);
            UserDTO updatedUser = userService.updateUser(id, userDto, request.getPassword());
            logger.info("User updated successfully: id={}", id);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            logger.error("Error updating user with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().body(new UserDTO(null, null, null));
        }
    }
}
