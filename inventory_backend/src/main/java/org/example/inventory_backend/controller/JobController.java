package org.example.inventory_backend.controller;

import org.example.inventory_backend.dto.JobDTO;
import org.example.inventory_backend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController extends GenericController<JobDTO, Long> {

    @Autowired
    public JobController(JobService employeeService){
        super(employeeService);
    }{}

}