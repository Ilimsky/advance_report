package org.example.reference_backend.mapper;

import org.example.reference_backend.dto.EmployeeDTO;
import org.example.reference_backend.model.Employee;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends GenericMapper<Employee, EmployeeDTO> {

    @Override
    EmployeeDTO toDTO(Employee employee);

    @Override
    Employee toEntity(EmployeeDTO employeeDTO);
}