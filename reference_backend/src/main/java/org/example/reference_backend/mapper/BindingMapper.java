package org.example.reference_backend.mapper;

import org.example.reference_backend.dto.BindingDTO;
import org.example.reference_backend.dto.DepartmentDTO;
import org.example.reference_backend.dto.EmployeeDTO;
import org.example.reference_backend.model.Binding;
import org.example.reference_backend.model.Department;
import org.example.reference_backend.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class BindingMapper {

    public BindingDTO toDTO(Binding binding) {
        BindingDTO dto = new BindingDTO();
        dto.setId(binding.getId());

        // Employee
        Employee employee = binding.getEmployee();
        EmployeeDTO employeeDTO = new EmployeeDTO(employee.getId(), employee.getName());
        dto.setEmployee(employeeDTO);

        // Department
        Department department = binding.getDepartment();
        DepartmentDTO departmentDTO = new DepartmentDTO(department.getId(), department.getName());
        dto.setDepartment(departmentDTO);

        return dto;
    }

    public Binding toEntity(BindingDTO dto, Department department, Employee employee) {
        Binding binding = new Binding();
        binding.setDepartment(department);
        binding.setEmployee(employee);
        return binding;
    }
}