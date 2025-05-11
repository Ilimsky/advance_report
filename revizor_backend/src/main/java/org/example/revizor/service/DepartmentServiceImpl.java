package org.example.revizor.service;

import org.example.revizor.dto.DepartmentDTO;
import org.example.revizor.mapper.DepartmentMapper;
import org.example.revizor.model.Department;
import org.example.revizor.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends GenericServiceImpl<Department, DepartmentDTO, Long> implements DepartmentService {


    @Autowired
    public DepartmentServiceImpl(DepartmentRepository repository, DepartmentMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void updateEntity(Department entity, DepartmentDTO dto) {
        entity.setName(dto.getName());
    }

}