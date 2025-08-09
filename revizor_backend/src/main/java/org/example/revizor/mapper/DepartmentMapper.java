package org.example.revizor.mapper;

import org.example.revizor.dto.EmployeeDTO;
import org.example.revizor.mapper.GenericMapper;
import org.example.revizor.model.Department;
import org.example.revizor.dto.DepartmentDTO;
import org.example.revizor.model.Employee;
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
