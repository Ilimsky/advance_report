package com.example.jobservice.service;


import com.example.jobservice.dto.JobDTO;

import java.util.List;
import java.util.Optional;

public interface JobService {

    JobDTO createJob(JobDTO jobDTO);

    List<JobDTO> getAllJobs();

    Optional<JobDTO> getJobById(Long id);

    JobDTO updateJob(Long id, JobDTO jobDTO);

    void delete(Long id);
}
