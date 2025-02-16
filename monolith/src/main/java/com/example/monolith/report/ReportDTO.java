package com.example.monolith.report;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

    private String dateReceived;
    private String amountIssued;
    private String dateApproved;
    private String purpose;
    private String recognizedAmount;
    private String comments;
}