package com.example.backend.job;

import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    public JobDTO toDTO(Job job) {
        if (job == null) {
            return null;
        }

        return new JobDTO(
                job.getId(),
                job.getName()
        );
    }

    public Job toEntity(JobDTO jobDTO) {
        if (jobDTO == null) {
            return null;
        }

        Job job = new Job();
        job.setId(jobDTO.getId());
        job.setName(jobDTO.getName());

        return job;
    }
}