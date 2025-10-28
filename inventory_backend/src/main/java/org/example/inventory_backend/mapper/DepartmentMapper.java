package org.example.inventory_backend.mapper;

import org.example.inventory_backend.dto.DepartmentDTO;
import org.example.inventory_backend.model.Department;
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

    public Department toEntity(DepartmentDTO departmentDTO) {
        if (departmentDTO == null) {
            return null;
        }

        Department department = new Department();
        department.setId(departmentDTO.getId());
        department.setName(departmentDTO.getName());

        return department;
    }
}