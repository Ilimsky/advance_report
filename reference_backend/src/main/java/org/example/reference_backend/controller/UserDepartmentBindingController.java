package org.example.reference_backend.controller;

import org.example.reference_backend.controller.user_controller.UserController;
import org.example.reference_backend.dto.UserDepartmentBindingDTO;
import org.example.reference_backend.exception.EntityNotFoundException;
import org.example.reference_backend.service.UserDepartmentBindingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user-departments")
@CrossOrigin(origins = "*")
public class UserDepartmentBindingController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserDepartmentBindingServiceImpl bindingService;

    @Autowired
    public UserDepartmentBindingController(UserDepartmentBindingServiceImpl bindingService) {
        this.bindingService = bindingService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<UserDepartmentBindingDTO>> getAllBindings() {
        List<UserDepartmentBindingDTO> bindings = bindingService.getAllBindings();
        return ResponseEntity.ok(bindings);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<?> createBinding(@RequestBody UserDepartmentBindingDTO bindingDTO) {
        try {
            UserDepartmentBindingDTO createdBinding = bindingService.createBinding(bindingDTO);
            return new ResponseEntity<>(createdBinding, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Внутренняя ошибка сервера"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<UserDepartmentBindingDTO>> getBindingsByUser(@PathVariable Long userId) {
        List<UserDepartmentBindingDTO> bindings = bindingService.getBindingsByUser(userId);
        return ResponseEntity.ok(bindings);
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<UserDepartmentBindingDTO>> getBindingsByDepartment(@PathVariable Long departmentId) {
        List<UserDepartmentBindingDTO> bindings = bindingService.getBindingsByDepartment(departmentId);
        return ResponseEntity.ok(bindings);
    }

    @GetMapping("/{bindingId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<UserDepartmentBindingDTO> getBindingById(@PathVariable Long bindingId) {
        UserDepartmentBindingDTO binding = bindingService.getBindingById(bindingId);
        return ResponseEntity.ok(binding);
    }

    @PutMapping("/{bindingId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<UserDepartmentBindingDTO> updateBinding(
            @PathVariable Long bindingId,
            @RequestBody UserDepartmentBindingDTO updatedBindingDTO) {
        logger.debug("PUT /api/user-departments/{} received", bindingId); // ← Добавить эту строку
        UserDepartmentBindingDTO updatedBinding = bindingService.updateBinding(bindingId, updatedBindingDTO);
        return ResponseEntity.ok(updatedBinding);
    }

    @DeleteMapping("/{bindingId}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> deleteBinding(@PathVariable Long bindingId) {
        bindingService.deleteBinding(bindingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> deleteBindingByUser(@PathVariable Long userId) {
        bindingService.deleteBindingByUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}