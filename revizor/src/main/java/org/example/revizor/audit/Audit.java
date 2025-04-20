package org.example.revizor.audit;

import jakarta.persistence.*;
import org.example.revizor.department.Department;
import org.example.revizor.employee.Employee;

import java.time.LocalDate;

@Entity
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Department department;
    @ManyToOne
    private Employee employee;
    private Long departmentIdentifier;
    private Long employeeIdentifier;
    private int auditNumber;
    private LocalDate dateReceived;
    private String purpose;
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
    public LocalDate getDateReceived() {
        return dateReceived;
    }
    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
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