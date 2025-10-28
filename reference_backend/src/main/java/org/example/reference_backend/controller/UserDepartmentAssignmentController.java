package org.example.reference_backend.controller;

import org.example.reference_backend.controller.user_controller.UserController;
import org.example.reference_backend.dto.UserDepartmentAssignmentDTO;
import org.example.reference_backend.exception.EntityNotFoundException;
import org.example.reference_backend.service.UserDepartmentAssignmentServiceImpl;
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
public class UserDepartmentAssignmentController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserDepartmentAssignmentServiceImpl bindingService;

    @Autowired
    public UserDepartmentAssignmentController(UserDepartmentAssignmentServiceImpl bindingService) {
        this.bindingService = bindingService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<UserDepartmentAssignmentDTO>> getAllBindings() {
        List<UserDepartmentAssignmentDTO> bindings = bindingService.getAllBindings();
        return ResponseEntity.ok(bindings);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<?> createBinding(@RequestBody UserDepartmentAssignmentDTO bindingDTO) {
        try {
            UserDepartmentAssignmentDTO createdBinding = bindingService.createBinding(bindingDTO);
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
    public ResponseEntity<List<UserDepartmentAssignmentDTO>> getBindingsByUser(@PathVariable Long userId) {
        List<UserDepartmentAssignmentDTO> bindings = bindingService.getBindingsByUser(userId);
        return ResponseEntity.ok(bindings);
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<UserDepartmentAssignmentDTO>> getBindingsByDepartment(@PathVariable Long departmentId) {
        List<UserDepartmentAssignmentDTO> bindings = bindingService.getBindingsByDepartment(departmentId);
        return ResponseEntity.ok(bindings);
    }

    @GetMapping("/{bindingId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<UserDepartmentAssignmentDTO> getBindingById(@PathVariable Long bindingId) {
        UserDepartmentAssignmentDTO binding = bindingService.getBindingById(bindingId);
        return ResponseEntity.ok(binding);
    }

    @PutMapping("/{bindingId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<UserDepartmentAssignmentDTO> updateBinding(
            @PathVariable Long bindingId,
            @RequestBody UserDepartmentAssignmentDTO updatedBindingDTO) {
        logger.debug("PUT /api/user-departments/{} received", bindingId); // ← Добавить эту строку
        UserDepartmentAssignmentDTO updatedBinding = bindingService.updateBinding(bindingId, updatedBindingDTO);
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