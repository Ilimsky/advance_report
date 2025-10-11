package org.example.reference_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EmployeeDepartmentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
