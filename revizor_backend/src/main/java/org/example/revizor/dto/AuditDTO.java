package org.example.revizor.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;


@Data
public class AuditDTO {

    private Long id;

    @NotNull(message = "Department ID cannot be null")
    @Min(value = 1, message = "Department ID must be greater than 0")
    private Long departmentId;

    @NotNull(message = "Employee ID cannot be null")
    @Min(value = 1, message = "Employee ID must be greater than 0")
    private Long employeeId;

    @NotNull(message = "Revizor ID cannot be null")
    @Min(value = 1, message = "Revizor ID must be greater than 0")
    private Long revizorId;

    @Min(value = 1, message = "Audit number must be greater than 0")
    private int auditNumber;

    @NotNull(message = "Department Identifier cannot be null")
    @Min(value = 1, message = "Department Identifier must be greater than 0")
    private Long departmentIdentifier;

    @NotNull(message = "Employee Identifier cannot be null")
    @Min(value = 1, message = "Employee Identifier must be greater than 0")
    private Long employeeIdentifier;

    @NotNull(message = "Revizor Identifier cannot be null")
    @Min(value = 1, message = "Revizor Identifier must be greater than 0")
    private Long revizorIdentifier;

    @NotNull(message = "Date Received cannot be null")
    @PastOrPresent(message = "Date Received must be in the past or present")
    private LocalDate dateReceived;

    @NotNull(message = "Ticket cannot be null")
    @Size(min = 1, max = 100, message = "Ticket length must be between 1 and 100 characters")
    private String ticket;

    @NotNull(message = "Description cannot be null")
    @Size(min = 1, max = 100, message = "Description length must be between 1 and 100 characters")
    private String description;

    @NotNull(message = "Purpose cannot be null")
    @Size(min = 1, max = 255, message = "Purpose length must be between 1 and 255 characters")
    private String purpose;

    @Size(max = 500, message = "Comments cannot exceed 500 characters")
    private String comments;

    // Default constructor and getters/setters (can be omitted if you use @Data)
    public AuditDTO() {
    }

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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getRevizorId() {
        return revizorId;
    }

    public void setRevizorId(Long revizorId) {
        this.revizorId = revizorId;
    }

    public int getAuditNumber() {
        return auditNumber;
    }

    public void setAuditNumber(int auditNumber) {
        this.auditNumber = auditNumber;
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
