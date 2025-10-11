package org.example.reference_backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDepartmentAssignmentDTO {
    private Long id;
    private EmployeeDTO employee;
    private DepartmentDTO department;

}