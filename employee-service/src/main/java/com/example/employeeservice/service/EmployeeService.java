package com.example.employeeservice.service;

import java.util.Optional;

import com.example.employeeservice.dto.EmployeeDTO;

public interface EmployeeService {

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    List<EmployeeDTO> getAllEmployees();

    Optional<EmployeeDTO> getEmployeeById(Long id);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    void delete(Long id);

}
