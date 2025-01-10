package com.example.jobservice.mapper;

import com.example.jobservice.dto.JobDTO;
import com.example.jobservice.entities.Job;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobMapper {

    // Преобразование из сущности в DTO
    public JobDTO toDTO(Job job) {
        if (job == null) {
            return null;
        }
        System.out.println("Mapping Job to DTO: " + job.getId() + ", Job Title: " + job.getJobTitle());
        return new JobDTO(
                job.getId(),
                job.getJobTitle()
        );
    }


    // Преобразование из DTO в сущность
    public Job toEntity(JobDTO jobDTO) {
        if (jobDTO == null) {
            return null;
        }

        // Логируем значения перед преобразованием
        System.out.println("Mapping JobDTO to entity: " + jobDTO.getId() + ", " + jobDTO.getJobTitle());

        Job job = new Job();
        job.setId(jobDTO.getId());
        job.setJobTitle(jobDTO.getJobTitle());  // Убедитесь, что это поле корректно маппится
        return job;
    }


    // Преобразование из списка сущностей в список DTO
    public List<JobDTO> toDTOList(List<Job> jobs) {
        if (jobs == null) {
            return null;
        }
        return jobs.stream()
                .map(this::toDTO)  // Используем this для вызова нестатического метода
                .collect(Collectors.toList());
    }

    // Преобразование из списка DTO в список сущностей
    public List<Job> toEntityList(List<JobDTO> jobDTOS) {
        if (jobDTOS == null) {
            return null;
        }
        return jobDTOS.stream()
                .map(this::toEntity)  // Используем this для вызова нестатического метода
                .collect(Collectors.toList());
    }
}