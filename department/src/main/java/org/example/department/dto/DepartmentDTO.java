package org.example.department.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DepartmentDTO{
    private Long departmentDTOId;
    private String departmentDTOName;
}
