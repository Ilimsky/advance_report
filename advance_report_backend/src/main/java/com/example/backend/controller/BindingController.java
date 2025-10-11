package com.example.backend.controller;

import com.example.backend.dto.BindingDTO;
import com.example.backend.service.BindingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee-departments")
@CrossOrigin(origins = "*")
public class BindingController {

    private final BindingServiceImpl bindingServiceImpl;

    @Autowired
    public BindingController(BindingServiceImpl bindingServiceImpl) {
        this.bindingServiceImpl = bindingServiceImpl;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<BindingDTO>> getAllBindings() {
        List<BindingDTO> bindings = bindingServiceImpl.getAllBindings();
        System.out.println("[DEBUG] Returning BindingDTO: " + bindings);

        return ResponseEntity.ok(bindings);
    }

    @GetMapping("/department/{departmentId}/job/{jobId}/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<BindingDTO>> getBindingsByIds(
            @PathVariable Long departmentId,
            @PathVariable Long jobId,
            @PathVariable Long employeeId) {
        List<BindingDTO> bindings = bindingServiceImpl.getBindingsByIds(departmentId, jobId, employeeId);
        return ResponseEntity.ok(bindings);
    }

    @GetMapping("/{bindingId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<BindingDTO> getBindingById(@PathVariable Long bindingId) {
        Optional<BindingDTO> bindingDTO = Optional.ofNullable(bindingServiceImpl.getBindingById(bindingId));
        return bindingDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
