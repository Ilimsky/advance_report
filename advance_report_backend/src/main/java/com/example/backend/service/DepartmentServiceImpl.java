package com.example.backend.service;

import com.example.backend.dto.DepartmentDTO;
import com.example.backend.exception.EntityNotFoundException;
import com.example.backend.mapper.DepartmentMapper;
import com.example.backend.model.Department;
import com.example.backend.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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