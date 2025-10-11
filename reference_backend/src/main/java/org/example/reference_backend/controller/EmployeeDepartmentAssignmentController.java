package org.example.reference_backend.controller;

import org.example.reference_backend.dto.EmployeeDepartmentAssignmentDTO;
import org.example.reference_backend.service.EmployeeDepartmentAssignmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee-departments")
@CrossOrigin(origins = "*")
public class EmployeeDepartmentAssignmentController {

    private final EmployeeDepartmentAssignmentServiceImpl bindingServiceImpl;

    @Autowired
    public EmployeeDepartmentAssignmentController(EmployeeDepartmentAssignmentServiceImpl bindingServiceImpl) {
        this.bindingServiceImpl = bindingServiceImpl;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<EmployeeDepartmentAssignmentDTO>> getAllBindings() {
        List<EmployeeDepartmentAssignmentDTO> bindings = bindingServiceImpl.getAllBindings();
//        System.out.println("[DEBUG] Returning BindingDTO: " + bindings);
        return ResponseEntity.ok(bindings);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<EmployeeDepartmentAssignmentDTO> createBinding(@RequestBody EmployeeDepartmentAssignmentDTO bindingDTO) {
        EmployeeDepartmentAssignmentDTO createdBinding = bindingServiceImpl.createBinding(bindingDTO);
        return new ResponseEntity<>(createdBinding, HttpStatus.CREATED);
    }

    @GetMapping("/department/{departmentId}/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<EmployeeDepartmentAssignmentDTO>> getBindingsByIds(
            @PathVariable Long departmentId,
            @PathVariable Long jobId,
            @PathVariable Long employeeId) {
        List<EmployeeDepartmentAssignmentDTO> bindings = bindingServiceImpl.getBindingsByIds(departmentId, employeeId);
        return ResponseEntity.ok(bindings);
    }

    @GetMapping("/{bindingId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<EmployeeDepartmentAssignmentDTO> getBindingById(@PathVariable Long bindingId) {
        Optional<EmployeeDepartmentAssignmentDTO> bindingDTO = Optional.ofNullable(bindingServiceImpl.getBindingById(bindingId));
        return bindingDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{bindingId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<EmployeeDepartmentAssignmentDTO> updateBinding(
            @PathVariable Long bindingId,
            @RequestBody EmployeeDepartmentAssignmentDTO updatedBindingDTO) {
        EmployeeDepartmentAssignmentDTO updatedBinding = bindingServiceImpl.updateBinding(bindingId, updatedBindingDTO);
        return new ResponseEntity<>(updatedBinding, HttpStatus.OK);
    }

    @DeleteMapping("/{bindingId}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> deleteBinding(@PathVariable Long bindingId) {
        bindingServiceImpl.deleteBinding(bindingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/department/{departmentId}/job/{jobId}/employee/{employeeId}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> deleteBindingsByIds(
            @PathVariable Long departmentId,
            @PathVariable Long jobId,
            @PathVariable Long employeeId) {
        bindingServiceImpl.deleteBindingsByIds(departmentId, jobId, employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
