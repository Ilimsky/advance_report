package com.example.backend.mapper;

import com.example.backend.dto.DepartmentDTO;
import com.example.backend.model.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper implements GenericMapper<Department, DepartmentDTO>{
    public DepartmentDTO toDTO(Department department) {
        if (department == null) {
            return null;
        }

        return new DepartmentDTO(
                department.getId(),
                department.getName()
        );
    }
}