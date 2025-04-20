package org.example.revizor.employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    Optional<EmployeeDTO> getEmployeeById(Long id);

    void deleteEmployee(Long id);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
}