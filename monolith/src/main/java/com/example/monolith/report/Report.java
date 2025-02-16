package com.example.monolith.report;

import com.example.monolith.account.Account;
import com.example.monolith.department.Department;
import com.example.monolith.employee.Employee;
import com.example.monolith.job.Job;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
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

    private String dateReceived;       // Дата получения д/с
    private String amountIssued;       // Выданная сумма
    private String dateApproved;       // Дата утверждения а/о
    private String purpose;            // Назначение
    private String recognizedAmount;   // Признанная сумма затрат по а/о
    private String comments;           // Комментарии
}
