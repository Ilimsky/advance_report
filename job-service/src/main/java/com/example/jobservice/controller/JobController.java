package com.example.jobservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.jobservice.dto.JobDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jobservice.exception.JobNotFoundException;
import com.example.jobservice.service.JobServiceImpl;

@RestController
@CrossOrigin(origins = "*")
public class JobController {
    private final JobServiceImpl jobServiceImpl;

    @Autowired
    public JobController(JobServiceImpl jobServiceImpl){
        this.jobServiceImpl = jobServiceImpl;
    }

    @PostMapping("/job")
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
        JobDTO createJobDTO = jobServiceImpl.createJob(jobDTO);
        return ResponseEntity.ok(createJobDTO);
    }

    @PostMapping("/jobs")
    public ResponseEntity<List<JobDTO>> createJobs(@RequestBody List<JobDTO> jobDTOS){
        List<JobDTO> createJobDTOS = jobDTOS.stream()
                .map(jobServiceImpl::createJob)
                .collect(Collectors.toList());
        return ResponseEntity.ok(createJobDTOS);
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobDTO>> getAllJobs(){
        List<JobDTO> jobDTOS = jobServiceImpl.getAllJobs();
        return ResponseEntity.ok(jobDTOS);
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        Optional<JobDTO> jobDTO = jobServiceImpl.getJobById(id);
        if(jobDTO.isPresent()){
            return ResponseEntity.ok(jobDTO.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/job/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable Long id, @RequestBody JobDTO jobDTO){
        try{
            JobDTO updateJobDTO = jobServiceImpl.updateJob(id, jobDTO);
            return ResponseEntity.ok(updateJobDTO);
        }catch(JobNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/job/{id}")
    public ResponseEntity<JobDTO> deleteJobById(@PathVariable Long id){
        try{
            jobServiceImpl.delete(id);
            return ResponseEntity.noContent().build();
        }catch(JobNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}