package org.example.inventory_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Binding {

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
