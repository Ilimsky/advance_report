package com.example.monolith.employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO createEmployee(EmployeeDTO departmentDTO);

    Optional<EmployeeDTO> getEmployeeById(Long id);

    void deleteEmployee(Long id);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO departmentDTO);
}