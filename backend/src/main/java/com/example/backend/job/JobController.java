package com.example.backend.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    private final JobServiceImpl jobServiceImpl;

    @Autowired
    public JobController(JobServiceImpl jobServiceImpl) {
        this.jobServiceImpl = jobServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobDTOs = jobServiceImpl.getAllJobs();
        return ResponseEntity.ok(jobDTOs);
    }

    // Создать новый департамент
    @PostMapping
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
        JobDTO createJobDTO = jobServiceImpl.createJob(jobDTO);
        return ResponseEntity.ok(createJobDTO);
    }

    // Получить департамент по ID
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
        Optional<JobDTO> jobDTO = jobServiceImpl.getJobById(id);
        if (jobDTO.isPresent()) {
            return ResponseEntity.ok(jobDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Удалить департамент по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<JobDTO> deleteJob(@PathVariable Long id) {
        try {
            jobServiceImpl.deleteJob(id);
            return ResponseEntity.noContent().build();
        } catch (JobNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Обновить департамент по ID
    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable Long id, @RequestBody JobDTO jobDTO) {
        try{
            JobDTO updateJobDTO = jobServiceImpl.updateJob(id, jobDTO);
            return ResponseEntity.ok(updateJobDTO);
        }catch(JobNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}