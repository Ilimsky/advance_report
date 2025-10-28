package org.example.jobservice;

import org.example.common_utils.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
//@CrossOrigin(origins = "*")
public class JobController extends GenericController<JobDTO, Long> {

    @Autowired
    public JobController(JobService employeeService){
        super(employeeService);
    }{}

}