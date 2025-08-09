package org.example.revizor.mapper;

import org.example.revizor.dto.DepartmentDTO;
import org.example.revizor.dto.EmployeeDTO;
import org.example.revizor.model.Department;
import org.example.revizor.model.Employee;
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