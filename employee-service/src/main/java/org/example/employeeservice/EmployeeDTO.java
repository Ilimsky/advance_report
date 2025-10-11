package org.example.employeeservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;
    private String name;

}