package org.example.inventory_backend.controller;

import org.example.inventory_backend.dto.DepartmentDTO;
import org.example.inventory_backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController extends GenericController<DepartmentDTO, Long> {

    @Autowired
    public DepartmentController(DepartmentService departmentService){
        super(departmentService);
    }{}

}