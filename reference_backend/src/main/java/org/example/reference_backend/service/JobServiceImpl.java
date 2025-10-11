package org.example.reference_backend.service;

import org.example.reference_backend.dto.JobDTO;
import org.example.reference_backend.mapper.JobMapper;
import org.example.reference_backend.model.Job;
import org.example.reference_backend.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl extends GenericServiceImpl<Job, JobDTO, Long> implements JobService {


    @Autowired
    public JobServiceImpl(JobRepository repository, JobMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void updateEntity(Job entity, JobDTO dto) {
        entity.setName(dto.getName());
    }

}