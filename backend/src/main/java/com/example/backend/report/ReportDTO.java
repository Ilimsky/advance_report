package com.example.backend.report;

import java.time.LocalDate;
import java.util.Date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    public Long getDepartmentIdentifier() {
        return departmentIdentifier;
    }

    public void setDepartmentIdentifier(Long departmentIdentifier) {
        this.departmentIdentifier = departmentIdentifier;
    }

    public Long getJobIdentifier() {
        return jobIdentifier;
    }

    public void setJobIdentifier(Long jobIdentifier) {
        this.jobIdentifier = jobIdentifier;
    }

    public Long getEmployeeIdentifier() {
        return employeeIdentifier;
    }

    public void setEmployeeIdentifier(Long employeeIdentifier) {
        this.employeeIdentifier = employeeIdentifier;
    }

    public Long getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(Long accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public LocalDate getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getAmountIssued() {
        return amountIssued;
    }

    public void setAmountIssued(String amountIssued) {
        this.amountIssued = amountIssued;
    }

    public LocalDate getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(LocalDate dateApproved) {
        this.dateApproved = dateApproved;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRecognizedAmount() {
        return recognizedAmount;
    }

    public void setRecognizedAmount(String recognizedAmount) {
        this.recognizedAmount = recognizedAmount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}