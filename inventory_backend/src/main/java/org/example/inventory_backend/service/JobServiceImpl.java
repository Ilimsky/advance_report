package org.example.inventory_backend.service;

import org.example.inventory_backend.dto.JobDTO;
import org.example.inventory_backend.model.Job;
import org.example.inventory_backend.mapper.JobMapper;
import org.example.inventory_backend.repository.JobRepository;
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