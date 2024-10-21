package com.example.employeeservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.employeeservice.dto.EmployeeDTO;
import com.example.employeeservice.exception.EmployeeNotFoundException;
import com.example.employeeservice.service.EmployeeServiceImpl;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeController {
    private final EmployeeServiceImpl employeeServiceImpl; 

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeServiceImpl){
        this.employeeServiceImpl = employeeServiceImpl;
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createEmployeeDTO = employeeServiceImpl.createEmployee(employeeDTO);
        return ResponseEntity.ok(createEmployeeDTO);
    }

    @PostMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> createEmployees(@RequestBody List<EmployeeDTO> employeeDTOs){
        List<EmployeeDTO> createEmployeeDTOs = employeeDTOs.stream()
        .map(employeeServiceImpl::createEmployee)
        .collect(Collectors.toList());
        return ResponseEntity.ok(createEmployeeDTOs);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        List<EmployeeDTO> employeeDTOs = employeeServiceImpl.getAllEmployees();
        return ResponseEntity.ok(employeeDTOs);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id){
        Optional<EmployeeDTO> employeeDTO = employeeServiceImpl.getEmployeeById(id);
        if(employeeDTO.isPresent()){
            return ResponseEntity.ok(employeeDTO.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO){
        try{
            EmployeeDTO updateEmployeeDTO = employeeServiceImpl.updateEmployee(id, employeeDTO);
            return ResponseEntity.ok(updateEmployeeDTO);
        }catch(EmployeeNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> deleteEmployeeById(@PathVariable Long id){
        try{
            employeeServiceImpl.delete(id);
            return ResponseEntity.noContent().build();
        }catch(EmployeeNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
