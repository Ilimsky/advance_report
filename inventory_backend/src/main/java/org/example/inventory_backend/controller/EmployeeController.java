package org.example.inventory_backend.controller;

import org.example.inventory_backend.dto.EmployeeDTO;
import org.example.inventory_backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController extends GenericController<EmployeeDTO, Long> {

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        super(employeeService);
    }{}

}