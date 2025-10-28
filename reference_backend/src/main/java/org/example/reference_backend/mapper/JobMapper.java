package org.example.reference_backend.mapper;

import org.example.reference_backend.dto.JobDTO;
import org.example.reference_backend.model.Job;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface JobMapper extends GenericMapper<Job, JobDTO>{

    @Override
    JobDTO toDTO(Job job);

    @Override
    Job toEntity(JobDTO jobDTO);
}