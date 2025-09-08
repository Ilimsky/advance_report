package com.example.backend.service;

import com.example.backend.dto.JobDTO;
import com.example.backend.mapper.JobMapper;
import com.example.backend.model.Job;
import com.example.backend.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl extends GenericServiceImpl<Job, JobDTO, Long> implements JobService {


    @Autowired
    public JobServiceImpl(JobRepository repository, JobMapper mapper) {
        super(repository, mapper);
    }

//    @Override
//    protected void updateEntity(Job entity, JobDTO dto) {
//        entity.setName(dto.getName());
//    }

}