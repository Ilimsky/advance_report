package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_department_binding")
@Data
public class UserDepartmentBinding {

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