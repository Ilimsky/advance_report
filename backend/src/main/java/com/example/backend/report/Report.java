package com.example.backend.report;

import com.example.backend.account.Account;
import com.example.backend.department.Department;
import com.example.backend.employee.Employee;
import com.example.backend.job.Job;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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