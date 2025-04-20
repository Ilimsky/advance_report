package com.example.backend.job;

import java.util.List;
import java.util.Optional;

public interface JobService {
    List<JobDTO> getAllJobs();

    JobDTO createJob(JobDTO jobDTO);

    Optional<JobDTO> getJobById(Long id);

    void deleteJob(Long id);

    JobDTO updateJob(Long id, JobDTO jobDTO);
}