package org.example.revizor.controller;

import org.example.revizor.config.MyUserDetails;
import org.example.revizor.dto.UserDto;
import org.example.revizor.model.Role;
import org.example.revizor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
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

    @GetMapping("/me")
    public ResponseEntity<UserDto> currentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof MyUserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        MyUserDetails details = (MyUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new UserDto(details.getUser().getId(), details.getUsername(), details.getUser().getRoles()));
    }

}
