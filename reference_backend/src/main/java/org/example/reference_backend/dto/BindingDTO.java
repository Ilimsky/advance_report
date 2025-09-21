package org.example.reference_backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BindingDTO {
    private Long id;
    private EmployeeDTO employee;
    private DepartmentDTO department;

}