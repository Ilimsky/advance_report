package org.example.reference_backend.service;

import org.example.reference_backend.dto.DepartmentDTO;
import org.example.reference_backend.mapper.DepartmentMapper;
import org.example.reference_backend.model.Department;
import org.example.reference_backend.repository.DepartmentRepository;
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