package org.example.employeeservice;

import org.example.common_utils.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends GenericMapper<Employee, EmployeeDTO> {

    @Override
    EmployeeDTO toDTO(Employee employee);

    @Override
    Employee toEntity(EmployeeDTO employeeDTO);
}