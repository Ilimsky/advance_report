package com.example.demo.controller;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.exception.DepartmentNotFoundException;
import com.example.demo.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class DepartmentController {
    private final DepartmentServiceImpl departmentServiceImpl;

    @Autowired
    public DepartmentController(DepartmentServiceImpl departmentServiceImpl){
        this.departmentServiceImpl = departmentServiceImpl;
    }

    @PostMapping("/department")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO createDepartmentDTO = departmentServiceImpl.createDepartment(departmentDTO);
        return ResponseEntity.ok(createDepartmentDTO);
    }

    @PostMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> createDepartments(@RequestBody List<DepartmentDTO> departmentDTOs){
        List<DepartmentDTO> createDepartmentDTOs = departmentDTOs.stream()
        .map(departmentServiceImpl::createDepartment)
        .collect(Collectors.toList());
        return ResponseEntity.ok(createDepartmentDTOs);
    }

    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(){
        List<DepartmentDTO> departmentDTOs = departmentServiceImpl.getAllDepartments();
        return ResponseEntity.ok(departmentDTOs);
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id){
        Optional<DepartmentDTO> departmentDTO = departmentServiceImpl.getDepartmentById(id);
        if(departmentDTO.isPresent()){
            return ResponseEntity.ok(departmentDTO.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/department/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO){
        try{
            DepartmentDTO updateDepartmentDTO = departmentServiceImpl.updateDepartment(id, departmentDTO);
            return ResponseEntity.ok(updateDepartmentDTO);
        }catch(DepartmentNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/department/{id}")
    public ResponseEntity<DepartmentDTO> deleteDepartmentById(@PathVariable Long id){
        try{
            departmentServiceImpl.delete(id);
            return ResponseEntity.noContent().build();
        }catch(DepartmentNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}