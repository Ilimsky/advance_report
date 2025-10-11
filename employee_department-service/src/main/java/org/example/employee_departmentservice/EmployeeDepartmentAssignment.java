package org.example.employee_departmentservice;

import jakarta.persistence.*;
import lombok.Data;
import org.example.departmentservice.Department;
import org.example.employeeservice.Employee;

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
