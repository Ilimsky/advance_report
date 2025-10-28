package org.example.revizor_backend.model;

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

//    @ManyToOne
//    @JoinColumn(name = "job_id")
//    private Job job;
}
