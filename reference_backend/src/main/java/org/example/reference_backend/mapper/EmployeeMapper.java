package org.example.reference_backend.mapper;

import org.example.reference_backend.dto.EmployeeDTO;
import org.example.reference_backend.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper implements GenericMapper<Employee, EmployeeDTO> {

    public EmployeeDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        return new EmployeeDTO(
                employee.getId(),
                employee.getName()
        );
    }

    public Employee toEntity(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());

        return employee;
    }
}