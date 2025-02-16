package com.example.departmentservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.departmentservice.exception.DepartmentNotFoundException;
import com.example.departmentservice.service.DepartmentServiceImpl;
import com.example.departmentservice.dto.DepartmentDTO;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

    private final DepartmentServiceImpl departmentServiceImpl;

    @Autowired
    public DepartmentController(DepartmentServiceImpl departmentServiceImpl) {
        this.departmentServiceImpl = departmentServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departmentDTOs = departmentServiceImpl.getAllDepartments();
        return ResponseEntity.ok(departmentDTOs);
    }

    // Создать новый департамент
    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO createDepartmentDTO = departmentServiceImpl.createDepartment(departmentDTO);
        return ResponseEntity.ok(createDepartmentDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        Optional<DepartmentDTO> departmentDTO = departmentServiceImpl.getDepartmentById(id);
        return departmentDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    // Удалить департамент по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentDTO> deleteDepartment(@PathVariable Long id) {
        try {
            departmentServiceImpl.deleteDepartment(id);
            return ResponseEntity.noContent().build();
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Обновить департамент по ID
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
        try{
            DepartmentDTO updateDepartmentDTO = departmentServiceImpl.updateDepartment(id, departmentDTO);
            return ResponseEntity.ok(updateDepartmentDTO);
        }catch(DepartmentNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}