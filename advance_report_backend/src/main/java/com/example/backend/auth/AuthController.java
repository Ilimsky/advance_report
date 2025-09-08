package com.example.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private DepartmentIdExtractor departmentIdExtractor;

    @Autowired
    private UserRepo userRepo; // Добавляем репозиторий

    @Autowired
    private PasswordEncoder passwordEncoder; // Добавляем энкодер

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            Map<String, Object> response = new HashMap<>();
            response.put("username", userDetails.getUsername());
            response.put("token", token);
            response.put("roles", userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).toList());
            response.put("departmentId", userDetails.getDepartmentId()); // Добавляем departmentId в ответ

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
        try {
            // Автоматически извлекаем departmentId из username
            Long departmentId = departmentIdExtractor.extractDepartmentIdFromUsername(registrationRequest.getUsername());

            User user = new User();
            user.setUsername(registrationRequest.getUsername());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRoles(registrationRequest.getRoles());
            user.setDepartmentId(departmentId); // Устанавливаем departmentId

            userRepo.save(user);

            return ResponseEntity.ok("User registered successfully");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
