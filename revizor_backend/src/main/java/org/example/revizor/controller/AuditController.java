package org.example.revizor.controller;

import jakarta.validation.Valid;
import org.example.revizor.dto.AuditDTO;
import org.example.revizor.service.AuditServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/audits")
@CrossOrigin(origins = "*")
public class AuditController {

    private final AuditServiceImpl auditServiceImpl;

    @Autowired
    public AuditController(AuditServiceImpl auditServiceImpl) {
        this.auditServiceImpl = auditServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<AuditDTO>> getAllAudits() {
        List<AuditDTO> audits = auditServiceImpl.getAllAudits();
        return ResponseEntity.ok(audits);
    }

    @PostMapping
    public ResponseEntity<AuditDTO> createAudit(@RequestBody @Valid AuditDTO auditDTO, BindingResult result) {
        // Проверка ошибок валидации
        if (result.hasErrors()) {
            // Если есть ошибки, возвращаем статус Bad Request с деталями ошибок
            return ResponseEntity.badRequest().body(null); // Вы можете настроить формат ошибок для более детального ответа
        }
        AuditDTO createdAudit = auditServiceImpl.createAudit(auditDTO);
        return new ResponseEntity<>(createdAudit, HttpStatus.CREATED);
    }

    @GetMapping("/department/{departmentId}/employee/{employeeId}/revizor/{revizorId}")
    public ResponseEntity<List<AuditDTO>> getAuditsByIds(@PathVariable Long departmentId,
                                                         @PathVariable Long employeeId,
                                                         @PathVariable Long revizorId) {
        List<AuditDTO> auditsByIds = auditServiceImpl.getAuditsByIds(departmentId, employeeId, revizorId);
        return ResponseEntity.ok(auditsByIds);
    }

    @GetMapping("/{auditId}")
    public ResponseEntity<AuditDTO> getAuditById(@PathVariable Long auditId) {
        Optional<AuditDTO> auditById = Optional.ofNullable(auditServiceImpl.getAuditById(auditId));
        return auditById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{auditId}")
    public ResponseEntity<AuditDTO> updateAudit(@PathVariable Long auditId, @RequestBody @Valid AuditDTO updatedAuditDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(null); // Обработка ошибок валидации
        }
        AuditDTO updatedAudit = auditServiceImpl.updateAudit(auditId, updatedAuditDTO);
        return new ResponseEntity<>(updatedAudit, HttpStatus.OK);
    }

    @DeleteMapping("/{auditId}")
    public ResponseEntity<Void> deleteAudit(@PathVariable Long auditId) {
        auditServiceImpl.deleteAudit(auditId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/department/{departmentId}/employee/{employeeId}/revizor/{revizorId}")
    public ResponseEntity<Void> deleteAuditsByIds(@PathVariable Long departmentId,
                                                  @PathVariable Long employeeId,
                                                  @PathVariable Long revizorId) {
        auditServiceImpl.deleteAuditsByIds(departmentId, employeeId, revizorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
