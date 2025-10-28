package org.example.usermanagementservice;

import jakarta.persistence.*;
import lombok.Data;
import org.example.departmentservice.Department;

@Entity
@Table(name = "user_department_binding")
@Data
public class UserDepartmentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}