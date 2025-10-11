package org.example.inventory_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BindingDTO {
    private Long id;
    private EmployeeDTO employee;
    private DepartmentDTO department;
}