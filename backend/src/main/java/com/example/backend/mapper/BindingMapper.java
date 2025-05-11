package com.example.backend.mapper;

import com.example.backend.dto.BindingDTO;
import com.example.backend.dto.DepartmentDTO;
import com.example.backend.dto.EmployeeDTO;
import com.example.backend.dto.JobDTO;
import com.example.backend.model.*;
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
        Job job = binding.getJob();
        JobDTO jobDTO = new JobDTO(job.getId(), job.getName());
        dto.setJob(jobDTO);

        return dto;
    }

    public Binding toEntity(BindingDTO dto, Department department, Job job, Employee employee) {
        Binding binding = new Binding();
        binding.setDepartment(department);
        binding.setJob(job);
        binding.setEmployee(employee);
        return binding;
    }

    private DepartmentDTO toDepartmentDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        return dto;
    }

    private JobDTO toJobDTO(Job job) {
        JobDTO dto = new JobDTO();
        dto.setId(job.getId());
        dto.setName(job.getName());
        return dto;
    }

    private EmployeeDTO toEmployeeDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        return dto;
    }
}


//    public BindingDTO toDTO(Binding binding) {
//        BindingDTO dto = new BindingDTO();
//        dto.setEmployeeId(binding.getEmployee().getId());
//        dto.setDepartmentId(binding.getDepartment().getId());
//        dto.setJobId(binding.getJob().getId());
//        return dto;
//    }

//    public Binding toEntity(BindingDTO dto, Department department, Job job, Employee employee) {
//        Binding binding = new Binding();
//        binding.setEmployee(employee);
//        binding.setDepartment(department);
//        binding.setJob(job);
//        return binding;
//    }
