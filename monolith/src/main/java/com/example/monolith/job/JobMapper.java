package com.example.monolith.job;


import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    // Преобразование из сущности в DTO
    public JobDTO toDTO(Job job) {
        if (job == null) {
            return null;
        }

        return new JobDTO(
                job.getId(),
                job.getName()
        );
    }

    // Преобразование из DTO в сущность
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