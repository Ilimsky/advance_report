package com.example.dept_service.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.dept_service.dto.DeptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dept_service.exception.DeptNotFoundException;
import com.example.dept_service.service.DeptServiceImpl;

@RestController
@CrossOrigin(origins = "*")
public class DeptController {
    private final DeptServiceImpl deptServiceImpl; 

    @Autowired
    public DeptController(DeptServiceImpl deptServiceImpl){
        this.deptServiceImpl = deptServiceImpl;
    }

    @PostMapping("/dept")
    public ResponseEntity<DeptDTO> createDept(@RequestBody DeptDTO deptDTO) {
        DeptDTO createDeptDTO = deptServiceImpl.createDept(deptDTO);
        return ResponseEntity.ok(createDeptDTO);
    }

    @PostMapping("/depts")
    public ResponseEntity<List<DeptDTO>> createDepts(@RequestBody List<DeptDTO> deptDTOs){
        List<DeptDTO> createDeptDTOs = deptDTOs.stream()
        .map(deptServiceImpl::createDept)
        .collect(Collectors.toList());
        return ResponseEntity.ok(createDeptDTOs);
    }

    @GetMapping("/depts")
    public ResponseEntity<List<DeptDTO>> getAllDepts(){
        List<DeptDTO> deptDTOs = deptServiceImpl.getAllDepts();
        return ResponseEntity.ok(deptDTOs);
    }

    @GetMapping("/dept/{id}")
    public ResponseEntity<DeptDTO> getDeptById(@PathVariable Long id){
        Optional<DeptDTO> deptDTO = deptServiceImpl.getDeptById(id);
        if(deptDTO.isPresent()){
            return ResponseEntity.ok(deptDTO.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/dept/{id}")
    public ResponseEntity<DeptDTO> updateDept(@PathVariable Long id, @RequestBody DeptDTO deptDTO){
        try{
            DeptDTO updateDeptDTO = deptServiceImpl.updateDept(id, deptDTO);
            return ResponseEntity.ok(updateDeptDTO);
        }catch(DeptNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/dept/{id}")
    public ResponseEntity<DeptDTO> deleteDeptById(@PathVariable Long id){
        try{
            deptServiceImpl.delete(id);
            return ResponseEntity.noContent().build();
        }catch(DeptNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}