package com.example.backend.controller;

import com.example.backend.dto.JobDTO;
import com.example.backend.dto.JobDTO;
import com.example.backend.service.JobService;
import com.example.backend.service.JobServiceImpl;
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