package com.example.jobservice.service;

import com.example.jobservice.dto.JobDTO;
import com.example.jobservice.entities.Job;
import com.example.jobservice.mapper.JobMapper;
import com.example.jobservice.repo.JobRepository;
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
    public JobDTO createJob(JobDTO jobDTO) {
        Job job = jobMapper.toEntity(jobDTO);
        Job savedJob = jobRepository.save(job);
        return jobMapper.toDTO(savedJob);
    }

    @Override
    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(jobMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<JobDTO> getJobById(Long id) {
        return jobRepository.findById(id)
                .map(jobMapper::toDTO);
    }

    @Override
    public JobDTO updateJob(Long id, JobDTO jobDTO) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        job.setJobTitle(jobDTO.getJobTitle());
        Job updatedJob = jobRepository.save(job);
        return jobMapper.toDTO(updatedJob);
    }

    @Override
    public void delete(Long id) {
        jobRepository.deleteById(id);
    }


}
