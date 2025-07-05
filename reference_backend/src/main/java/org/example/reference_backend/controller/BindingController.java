package org.example.reference_backend.controller;

import org.example.reference_backend.dto.BindingDTO;
import org.example.reference_backend.service.BindingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Получение всех привязок сотрудников
    @GetMapping
    public ResponseEntity<List<BindingDTO>> getAllBindings() {
        List<BindingDTO> bindings = bindingServiceImpl.getAllBindings();
        System.out.println("[DEBUG] Returning BindingDTO: " + bindings);

        return ResponseEntity.ok(bindings);
    }

    // Создание новой привязки сотрудника
    @PostMapping
    public ResponseEntity<BindingDTO> createBinding(@RequestBody BindingDTO bindingDTO) {
        BindingDTO createdBinding = bindingServiceImpl.createBinding(bindingDTO);
        return new ResponseEntity<>(createdBinding, HttpStatus.CREATED);
    }

    // Получение привязок сотрудников по ID департамента, должности и сотрудника
    @GetMapping("/department/{departmentId}/job/{jobId}/employee/{employeeId}")
    public ResponseEntity<List<BindingDTO>> getBindingsByIds(
            @PathVariable Long departmentId,
            @PathVariable Long jobId,
            @PathVariable Long employeeId) {
        List<BindingDTO> bindings = bindingServiceImpl.getBindingsByIds(departmentId, jobId, employeeId);
        return ResponseEntity.ok(bindings);
    }

    // Получение привязки сотрудника по ID
    @GetMapping("/{bindingId}")
    public ResponseEntity<BindingDTO> getBindingById(@PathVariable Long bindingId) {
        Optional<BindingDTO> bindingDTO = Optional.ofNullable(bindingServiceImpl.getBindingById(bindingId));
        return bindingDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Обновление привязки сотрудника
    @PutMapping("/{bindingId}")
    public ResponseEntity<BindingDTO> updateBinding(
            @PathVariable Long bindingId,
            @RequestBody BindingDTO updatedBindingDTO) {
        BindingDTO updatedBinding = bindingServiceImpl.updateBinding(bindingId, updatedBindingDTO);
        return new ResponseEntity<>(updatedBinding, HttpStatus.OK);
    }

    // Удаление привязки сотрудника по ID
    @DeleteMapping("/{bindingId}")
    public ResponseEntity<Void> deleteBinding(@PathVariable Long bindingId) {
        bindingServiceImpl.deleteBinding(bindingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Удаление привязок сотрудников по ID департамента, должности и сотрудника
    @DeleteMapping("/department/{departmentId}/job/{jobId}/employee/{employeeId}")
    public ResponseEntity<Void> deleteBindingsByIds(
            @PathVariable Long departmentId,
            @PathVariable Long jobId,
            @PathVariable Long employeeId) {
        bindingServiceImpl.deleteBindingsByIds(departmentId, jobId, employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
