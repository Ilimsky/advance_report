package com.example.backend.report;

import com.example.backend.account.Account;
import com.example.backend.department.Department;
import com.example.backend.employee.Employee;
import com.example.backend.job.Job;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    public ReportDTO toDTO(Report report) {
        ReportDTO dto = new ReportDTO();
        dto.setId(report.getId());
        dto.setDepartmentId(report.getDepartment().getId());
        dto.setJobId(report.getJob().getId());
        dto.setEmployeeId(report.getEmployee().getId());
        dto.setAccountId(report.getAccount().getId());
        dto.setReportNumber(report.getReportNumber());
        dto.setDepartmentIdentifier(report.getDepartmentIdentifier());
        dto.setJobIdentifier(report.getJobIdentifier());
        dto.setEmployeeIdentifier(report.getEmployeeIdentifier());
        dto.setAccountIdentifier(report.getAccountIdentifier());

        dto.setDateReceived(report.getDateReceived());
        dto.setAmountIssued(report.getAmountIssued());
        dto.setDateApproved(report.getDateApproved());
        dto.setPurpose(report.getPurpose());
        dto.setRecognizedAmount(report.getRecognizedAmount());
        dto.setComments(report.getComments());
        return dto;
    }

    public Report toEntity(ReportDTO dto, Department department, Job job, Employee employee, Account account) {
        Report report = new Report();
        report.setDepartment(department);
        report.setJob(job);
        report.setEmployee(employee);
        report.setAccount(account);
        report.setReportNumber(dto.getReportNumber());
        report.setDepartmentIdentifier(dto.getDepartmentIdentifier());
        report.setJobIdentifier(dto.getJobIdentifier());
        report.setEmployeeIdentifier(dto.getEmployeeIdentifier());
        report.setAccountIdentifier(dto.getAccountIdentifier());

        // Новые поля
        report.setDateReceived(dto.getDateReceived());
        report.setAmountIssued(dto.getAmountIssued());
        report.setDateApproved(dto.getDateApproved());
        report.setPurpose(dto.getPurpose());
        report.setRecognizedAmount(dto.getRecognizedAmount());
        report.setComments(dto.getComments());

        return report;
    }
}