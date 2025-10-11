package org.example.revizor_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Department department;
    @ManyToOne
    @NotNull
    private Employee employee;

    @ManyToOne
    @NotNull
    private Revizor revizor;

    @NotNull
    @Min(1)
    private Long departmentIdentifier;

    @NotNull
    @Min(1)
    private Long employeeIdentifier;

    @NotNull
    @Min(1)
    private Long revizorIdentifier;

    @Min(1)
    private int auditNumber;

    @NotNull
    @PastOrPresent
    private LocalDate dateReceived;

    @NotNull
    @Size(min = 1, max = 100)
    private String ticket;

    @NotNull
    @Size(min = 1, max = 100)
    private String description;

    @NotNull
    @Size(min = 1, max = 255)
    private String purpose;

    @Size(max = 500)
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Revizor getRevizor() {
        return revizor;
    }

    public void setRevizor(Revizor revizor) {
        this.revizor = revizor;
    }

    public Long getDepartmentIdentifier() {
        return departmentIdentifier;
    }

    public void setDepartmentIdentifier(Long departmentIdentifier) {
        this.departmentIdentifier = departmentIdentifier;
    }

    public Long getEmployeeIdentifier() {
        return employeeIdentifier;
    }

    public void setEmployeeIdentifier(Long employeeIdentifier) {
        this.employeeIdentifier = employeeIdentifier;
    }

    public Long getRevizorIdentifier() {
        return revizorIdentifier;
    }

    public void setRevizorIdentifier(Long revizorIdentifier) {
        this.revizorIdentifier = revizorIdentifier;
    }

    public int getAuditNumber() {
        return auditNumber;
    }

    public void setAuditNumber(int auditNumber) {
        this.auditNumber = auditNumber;
    }

    public LocalDate getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
