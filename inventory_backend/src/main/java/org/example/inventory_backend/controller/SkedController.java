package org.example.inventory_backend.controller;

import org.example.inventory_backend.dto.SkedDTO;
import org.example.inventory_backend.service.SkedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/skeds")
@CrossOrigin(origins = "*")
public class SkedController {

    private final SkedServiceImpl skedServiceImpl;
    @Autowired
    public SkedController(SkedServiceImpl skedServiceImpl) {
        this.skedServiceImpl = skedServiceImpl;
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<SkedDTO>> getAllSkedsPaged(@PageableDefault(size = 20) Pageable pageable) {
        Page<SkedDTO> pagedSkeds = skedServiceImpl.getAllSkedsPaged(pageable);
        return ResponseEntity.ok(pagedSkeds);
    }

    @GetMapping("/department/{departmentId}/paged")
    public ResponseEntity<Page<SkedDTO>> getSkedsByDepartmentPaged(
            @PathVariable Long departmentId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<SkedDTO> pagedSkeds = skedServiceImpl.getSkedsByDepartmentIdPaged(departmentId, pageable);
        return ResponseEntity.ok(pagedSkeds);
    }

    @GetMapping
    public ResponseEntity<List<SkedDTO>> getAllSkeds() {
        List<SkedDTO> skeds = skedServiceImpl.getAllSkeds();
        return ResponseEntity.ok(skeds);
    }

    @PostMapping
    public ResponseEntity<SkedDTO> createSked(@RequestBody SkedDTO skedDTO) {
        SkedDTO createdSked = skedServiceImpl.createSked(skedDTO);
        return new ResponseEntity<>(createdSked, HttpStatus.CREATED);
    }

    @GetMapping("/{skedId}")
    public ResponseEntity<SkedDTO> getSkedById(@PathVariable Long skedId) {
        Optional<SkedDTO> skedById = Optional.ofNullable(skedServiceImpl.getSkedById(skedId));
        return skedById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<SkedDTO>> getSkedsByDepartment(@PathVariable Long departmentId) {
        List<SkedDTO> skeds = skedServiceImpl.getSkedsByDepartmentId(departmentId);
        return ResponseEntity.ok(skeds);
    }

    @PutMapping("/{skedId}")
    public ResponseEntity<SkedDTO> updateSked(@PathVariable Long skedId, @RequestBody SkedDTO updatedSkedDTO) {
        SkedDTO updatedSked = skedServiceImpl.updateSked(skedId, updatedSkedDTO);
        return new ResponseEntity<>(updatedSked, HttpStatus.OK);
    }

    @DeleteMapping("/{skedId}")
    public ResponseEntity<Void> deleteSked(@PathVariable Long skedId) {
        skedServiceImpl.deleteSked(skedId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}