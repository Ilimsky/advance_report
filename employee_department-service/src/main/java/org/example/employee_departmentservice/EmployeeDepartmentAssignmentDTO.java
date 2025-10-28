package org.example.employee_departmentservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.departmentservice.DepartmentDTO;
import org.example.employeeservice.EmployeeDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDepartmentAssignmentDTO {
    private Long id;
    private EmployeeDTO employee;
    private DepartmentDTO department;

}