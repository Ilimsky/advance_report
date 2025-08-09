package org.example.reference_backend.controller;

import org.example.reference_backend.dto.JobDTO;
import org.example.reference_backend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController extends GenericController<JobDTO, Long> {

    @Autowired
    public JobController(JobService employeeService){
        super(employeeService);
    }{}

}