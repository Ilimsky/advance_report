package org.example.reference_backend.controller;

import org.example.reference_backend.dto.AccountDTO;
import org.example.reference_backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController extends GenericController<AccountDTO, Long> {

    public AccountController(AccountService accountService) {
        super(accountService);
    }

    @Override
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAll() {
        return super.getAll();
    }

    @Override
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    @PostMapping
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO dto) {
        return super.create(dto);
    }

    @Override
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable Long id, @RequestBody AccountDTO dto) {
        return super.update(id, dto);
    }

    @Override
    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        super.delete(id);
        return ResponseEntity.noContent().build();
    }
}
