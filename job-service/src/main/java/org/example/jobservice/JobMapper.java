package org.example.jobservice;

import org.example.common_utils.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper extends GenericMapper<Job, JobDTO> {

    @Override
    JobDTO toDTO(Job job);

    @Override
    Job toEntity(JobDTO jobDTO);
}