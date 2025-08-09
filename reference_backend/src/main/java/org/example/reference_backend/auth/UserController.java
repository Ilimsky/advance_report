package org.example.reference_backend.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final UserRepo userRepo;


    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, UserRepo userRepo) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
    }

    @PostMapping("/createUser")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request, @RequestHeader("Authorization") String authHeader) {
        try {
            logger.info("Attempt to create new user. Username: {}", request.getUsername());
            logger.debug("Full request data: {}", request.toString());
            logger.info("Authorization header: {}", authHeader);

            if (userRepo.existsByUsername(request.getUsername())) {
                logger.warn("Username already exists: {}", request.getUsername());
                return ResponseEntity.badRequest().body("Username already exists");
            }

            logger.debug("Creating DTO for new user");
            UserDto userDto = new UserDto(null, request.getUsername(), request.getRoles());

            logger.info("Creating user with roles: {}", request.getRoles());
            UserDto createdUser = userService.createUser(userDto, request.getPassword());

            logger.info("User created successfully. ID: {}, Username: {}",
                    createdUser.getId(), createdUser.getUsername());
            logger.debug("Full created user data: {}", createdUser.toString());


            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            logger.error("Error creating user", e);

            return ResponseEntity.internalServerError().body("Error creating user: " + e.getMessage());
        }
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasRole('USER')")  // Use hasRole for role-based access control
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasRole('ADMIN')")  // Use hasRole for role-based access control
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        String password = (String) payload.get("password");
        List<String> rolesStr = (List<String>) payload.get("roles");

        Set<Role> roles = rolesStr.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());

        UserDto dto = new UserDto(null, name, roles);
        UserDto saved = userService.createUser(dto, password);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request,
            @RequestHeader("Authorization") String authHeader) {
        try {
            logger.info("Attempt to update user with ID: {}", id);

            UserDto userDto = new UserDto(id, request.getUsername(), request.getRoles());
            UserDto updatedUser = userService.updateUser(id, userDto, request.getPassword());

            logger.info("User updated successfully. ID: {}", id);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            logger.error("Error updating user", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
