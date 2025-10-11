package org.example.usermanagementservice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.departmentservice.DepartmentDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDepartmentAssignmentDTO {
    private Long id;
    private UserDTO user;
    private DepartmentDTO department;

}