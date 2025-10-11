package com.example.backend.mapper;

import com.example.backend.dto.EmployeeDTO;
import com.example.backend.model.Employee;
import com.example.backend.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}