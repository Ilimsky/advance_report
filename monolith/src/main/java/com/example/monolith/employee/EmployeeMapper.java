package com.example.monolith.employee;


import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    // Преобразование из сущности в DTO
    public EmployeeDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        return new EmployeeDTO(
                employee.getId(),
                employee.getName()
        );
    }

    // Преобразование из DTO в сущность
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