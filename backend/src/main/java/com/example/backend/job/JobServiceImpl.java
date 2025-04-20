package com.example.backend.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    @Override
    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(jobMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public JobDTO createJob(JobDTO jobDTO) {
        Job job = jobMapper.toEntity(jobDTO);
        Job savedJob = jobRepository.save(job);
        return jobMapper.toDTO(savedJob);
    }

    @Override
    public Optional<JobDTO> getJobById(Long id) {
        return jobRepository.findById(id)
                .map(jobMapper::toDTO);
    }

    @Override
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public JobDTO updateJob(Long id, JobDTO jobDTO) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found"));

        job.setName(jobDTO.getName());

        Job updatedJob = jobRepository.save(job);
        return jobMapper.toDTO(updatedJob);
    }
}