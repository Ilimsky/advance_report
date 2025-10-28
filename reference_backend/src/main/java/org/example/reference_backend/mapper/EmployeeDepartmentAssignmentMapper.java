package org.example.reference_backend.mapper;

import org.example.reference_backend.dto.EmployeeDepartmentAssignmentDTO;
import org.example.reference_backend.dto.DepartmentDTO;
import org.example.reference_backend.dto.EmployeeDTO;
import org.example.reference_backend.model.EmployeeDepartmentAssignment;
import org.example.reference_backend.model.Department;
import org.example.reference_backend.model.Employee;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, DepartmentMapper.class})
public interface EmployeeDepartmentAssignmentMapper {

    EmployeeDepartmentAssignmentDTO toDTO(EmployeeDepartmentAssignment assignment);

    @Mapping(target = "id", ignore = true)
    EmployeeDepartmentAssignment toEntity(EmployeeDepartmentAssignmentDTO dto,
                                          @Context Department department,
                                          @Context Employee employee);
}