package com.example.backend.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeeDTOs = employeeServiceImpl.getAllEmployees();
        return ResponseEntity.ok(employeeDTOs);
    }

    // Создать новый департамент
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createEmployeeDTO = employeeServiceImpl.createEmployee(employeeDTO);
        return ResponseEntity.ok(createEmployeeDTO);
    }

    // Получить департамент по ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeDTO> employeeDTO = employeeServiceImpl.getEmployeeById(id);
        if (employeeDTO.isPresent()) {
            return ResponseEntity.ok(employeeDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Удалить департамент по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable Long id) {
        try {
            employeeServiceImpl.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Обновить департамент по ID
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        try{
            EmployeeDTO updateEmployeeDTO = employeeServiceImpl.updateEmployee(id, employeeDTO);
            return ResponseEntity.ok(updateEmployeeDTO);
        }catch(EmployeeNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}