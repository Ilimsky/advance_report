package org.example.inventory_backend.controller;

import org.example.inventory_backend.dto.SkedDTO;
import org.example.inventory_backend.dto.SkedHistoryDTO;
import org.example.inventory_backend.dto.TransferRequest;
import org.example.inventory_backend.dto.WriteOffRequest;
import org.example.inventory_backend.mapper.SkedHistoryMapper;
import org.example.inventory_backend.model.SkedHistory;
import org.example.inventory_backend.repository.SkedHistoryRepository;
import org.example.inventory_backend.service.SkedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/skeds")
@CrossOrigin(origins = "*")
public class SkedController {

    private final SkedServiceImpl skedServiceImpl;
    private final SkedHistoryRepository historyRepository;
    private final SkedHistoryMapper historyMapper;

    @Autowired
    public SkedController(SkedServiceImpl skedServiceImpl, SkedHistoryRepository historyRepository, SkedHistoryMapper historyMapper) {
        this.skedServiceImpl = skedServiceImpl;
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
    }

    @GetMapping("/paged")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<Page<SkedDTO>> getAllSkedsPaged(@PageableDefault(size = 20) Pageable pageable) {
        Page<SkedDTO> pagedSkeds = skedServiceImpl.getAllSkedsPaged(pageable);
        return ResponseEntity.ok(pagedSkeds);
    }

    @GetMapping("/department/{departmentId}/paged")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<Page<SkedDTO>> getSkedsByDepartmentPaged(
            @PathVariable Long departmentId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<SkedDTO> pagedSkeds = skedServiceImpl.getSkedsByDepartmentIdPaged(departmentId, pageable);
        return ResponseEntity.ok(pagedSkeds);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<SkedDTO>> getAllSkeds() {
        List<SkedDTO> skeds = skedServiceImpl.getAllSkeds();
        return ResponseEntity.ok(skeds);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<SkedDTO> createSked(@RequestBody SkedDTO skedDTO) {
        SkedDTO createdSked = skedServiceImpl.createSked(skedDTO);
        return new ResponseEntity<>(createdSked, HttpStatus.CREATED);
    }

    @GetMapping("/{skedId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<SkedDTO> getSkedById(@PathVariable Long skedId) {
        Optional<SkedDTO> skedById = Optional.ofNullable(skedServiceImpl.getSkedById(skedId));
        return skedById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<SkedDTO>> getSkedsByDepartment(@PathVariable Long departmentId) {
        List<SkedDTO> skeds = skedServiceImpl.getSkedsByDepartmentId(departmentId);
        return ResponseEntity.ok(skeds);
    }

    @PutMapping("/{skedId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<SkedDTO> updateSked(@PathVariable Long skedId, @RequestBody SkedDTO updatedSkedDTO) {
        SkedDTO updatedSked = skedServiceImpl.updateSked(skedId, updatedSkedDTO);
        return new ResponseEntity<>(updatedSked, HttpStatus.OK);
    }

    @DeleteMapping("/{skedId}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> deleteSked(@PathVariable Long skedId) {
        skedServiceImpl.deleteSked(skedId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{skedId}/release-number")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<Void> releaseSkedNumber(@PathVariable Long skedId) {
        skedServiceImpl.releaseSkedNumber(skedId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{skedId}/history")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<SkedHistoryDTO>> getSkedHistory(@PathVariable Long skedId) {
        List<SkedHistory> history = historyRepository.findBySkedIdOrderByActionDateDesc(skedId);
        return ResponseEntity.ok(history.stream()
                .map(historyMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping("/{skedId}/write-off")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> writeOffSked(@PathVariable Long skedId,
                                             @RequestBody WriteOffRequest request) {
        skedServiceImpl.writeOffSked(skedId, request.getReason());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{skedId}/transfer")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<SkedDTO> transferSked(@PathVariable Long skedId,
                                                @RequestBody TransferRequest request) {
        SkedDTO result = skedServiceImpl.transferSked(skedId, request.getNewDepartmentId(), request.getReason());
        return ResponseEntity.ok(result);
    }
}