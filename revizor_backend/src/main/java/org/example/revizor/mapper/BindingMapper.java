package org.example.revizor.mapper;

import org.example.revizor.dto.BindingDTO;
import org.example.revizor.dto.DepartmentDTO;
import org.example.revizor.dto.EmployeeDTO;
import org.example.revizor.model.Binding;
import org.example.revizor.model.Department;
import org.example.revizor.model.Employee;
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

        // Job
//        Job job = binding.getJob();
//        JobDTO jobDTO = new JobDTO(job.getId(), job.getName());
//        dto.setJob(jobDTO);

        return dto;
    }

    public Binding toEntity(BindingDTO dto, Department department, Employee employee) {
        Binding binding = new Binding();
        binding.setDepartment(department);
//        binding.setJob(job);
        binding.setEmployee(employee);
        return binding;
    }

//    private DepartmentDTO toDepartmentDTO(Department department) {
//        DepartmentDTO dto = new DepartmentDTO();
//        dto.setId(department.getId());
//        dto.setName(department.getName());
//        return dto;
//    }

//    private JobDTO toJobDTO(Job job) {
//        JobDTO dto = new JobDTO();
//        dto.setId(job.getId());
//        dto.setName(job.getName());
//        return dto;
//    }

//    private EmployeeDTO toEmployeeDTO(Employee employee) {
//        EmployeeDTO dto = new EmployeeDTO();
//        dto.setId(employee.getId());
//        dto.setName(employee.getName());
//        return dto;
//    }
}