package org.example.reference_backend.mapper;

import org.example.reference_backend.dto.AccountDTO;
import org.example.reference_backend.dto.DepartmentDTO;
import org.example.reference_backend.model.Account;
import org.example.reference_backend.model.Department;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface DepartmentMapper extends GenericMapper<Department, DepartmentDTO>{

    @Override
    DepartmentDTO toDTO(Department department);

    @Override
    Department toEntity(DepartmentDTO  departmentDTO);
}
