package com.example.backend.model;

import com.example.backend.model.Account;
import com.example.backend.model.Department;
import com.example.backend.model.Employee;
import com.example.backend.model.Job;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Job job;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Account account;

    private int reportNumber;
    private Long departmentIdentifier;
    private Long jobIdentifier;
    private Long employeeIdentifier;
    private Long accountIdentifier;

    private LocalDate dateReceived;
    private String amountIssued;

    private LocalDate dateApproved;
    private String purpose;
    private String recognizedAmount;
    private String comments;
}