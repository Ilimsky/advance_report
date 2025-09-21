package org.example.reference_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reference_backend.service.GenericService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericController<D, ID> {
    protected final GenericService<D, ID> service;

    protected GenericController(GenericService<D, ID> service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<D>> getAll() {
        List<D> result = service.getAll();
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Логируем тело ответа
            String jsonResponse = mapper.writeValueAsString(result);
//            System.out.println("JSON Response for getAll: " + jsonResponse);
            // Логируем заголовки
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
//            System.out.println("Response Headers for getAll: " + headers);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(result);
        } catch (Exception e) {
//            System.err.println("Error serializing response: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<D> create(@RequestBody D dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<D> getById(@PathVariable ID id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<D> update(@PathVariable ID id, @RequestBody D dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
}
