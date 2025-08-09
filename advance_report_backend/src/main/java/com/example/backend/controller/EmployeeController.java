package com.example.backend.controller;

import com.example.backend.dto.DepartmentDTO;
import com.example.backend.dto.EmployeeDTO;
import com.example.backend.service.DepartmentService;
import com.example.backend.service.EmployeeService;
import com.example.backend.service.EmployeeServiceImpl;
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