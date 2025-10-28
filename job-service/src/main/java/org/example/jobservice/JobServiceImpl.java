package org.example.jobservice;

import org.example.common_utils.GenericServiceImpl;
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