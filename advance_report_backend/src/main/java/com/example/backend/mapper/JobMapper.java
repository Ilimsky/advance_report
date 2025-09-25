package com.example.backend.mapper;

import com.example.backend.dto.JobDTO;
import com.example.backend.model.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper implements GenericMapper<Job, JobDTO>{

    public JobDTO toDTO(Job job) {
        if (job == null) {
            return null;
        }

        return new JobDTO(
                job.getId(),
                job.getName()
        );
    }
}