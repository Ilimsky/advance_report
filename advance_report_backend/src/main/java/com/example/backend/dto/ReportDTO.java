package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private Long id;
    private Long departmentId;
    private Long jobId;
    private Long employeeId;
    private Long accountId;
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