package org.example.inventory_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Sked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Job job;

    @ManyToOne
    private Employee employee;

    private Long departmentIdentifier;
    private Long jobIdentifier;
    private Long employeeIdentifier;

    private LocalDate dateReceived;
    private int skedNumber;
    private String itemName;
    private String serialNumber;
    private int count;
    private String measure;
    private Double price;
    private String place;

    private String comments;
}