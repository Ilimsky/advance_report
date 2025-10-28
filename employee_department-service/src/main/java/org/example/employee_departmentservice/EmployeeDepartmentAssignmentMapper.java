package org.example.employee_departmentservice;

import org.example.departmentservice.Department;
import org.example.departmentservice.DepartmentMapper;
import org.example.employeeservice.Employee;
import org.example.employeeservice.EmployeeMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, DepartmentMapper.class})
public interface EmployeeDepartmentAssignmentMapper {

    EmployeeDepartmentAssignmentDTO toDTO(EmployeeDepartmentAssignment assignment);

    @Mapping(target = "id", ignore = true)
    EmployeeDepartmentAssignment toEntity(EmployeeDepartmentAssignmentDTO dto,
                                          @Context Department department,
                                          @Context Employee employee);
}