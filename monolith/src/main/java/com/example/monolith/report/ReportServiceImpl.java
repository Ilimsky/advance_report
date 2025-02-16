package com.example.monolith.report;

import com.example.monolith.account.Account;
import com.example.monolith.account.AccountRepository;
import com.example.monolith.department.Department;
import com.example.monolith.department.DepartmentRepository;
import com.example.monolith.employee.Employee;
import com.example.monolith.employee.EmployeeRepository;
import com.example.monolith.job.Job;
import com.example.monolith.job.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final DepartmentRepository departmentRepository;
    private final JobRepository jobRepository;
    private final EmployeeRepository employeeRepository;
    private final AccountRepository accountRepository;
    private final ReportMapper reportMapper;

    public ReportServiceImpl(ReportRepository reportRepository, DepartmentRepository departmentRepository, JobRepository jobRepository, EmployeeRepository employeeRepository, AccountRepository accountRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.departmentRepository = departmentRepository;
        this.jobRepository = jobRepository;
        this.employeeRepository = employeeRepository;
        this.accountRepository = accountRepository;
        this.reportMapper = reportMapper;
    }

//    @Override
//    public ReportDTO createReport(Long departmentId, Long jobId, Long employeeId, Long accountId) {
//        Department department = departmentRepository.findById(departmentId)
//                .orElseThrow(() -> new RuntimeException("Department not found"));
//        Job job = jobRepository.findById(jobId)
//                .orElseThrow(() -> new RuntimeException("Job not found"));
//
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        Account account = accountRepository.findById(accountId)
//                .orElseThrow(() -> new RuntimeException("Account not found"));
//
//        List<Report> reports = reportRepository.findByDepartmentById(departmentId);
//        int nextReportNumber = reports.size() + 1;
//
//        Report report = new Report();
//        report.setDepartment(department);
//        report.setJob(job);
//        report.setEmployee(employee);
//        report.setAccount(account);
//        report.setReportNumber(nextReportNumber);
//        report.setDepartmentIdentifier(department.getId());
//        report.setJobIdentifier(job.getId());
//        report.setEmployeeIdentifier(employee.getId());
//        report.setAccountIdentifier(account.getId());
//
//        Report savedReport = reportRepository.save(report);
//        return reportMapper.toDTO(savedReport);
//    }

    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        Department department = departmentRepository.findById(reportDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        Job job = jobRepository.findById(reportDTO.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));
        Employee employee = employeeRepository.findById(reportDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Account account = accountRepository.findById(reportDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        List<Report> reports = reportRepository.findByDepartmentById(reportDTO.getDepartmentId());
        int nextReportNumber = reports.size() + 1;

        Report report = reportMapper.toEntity(reportDTO, department, job, employee, account);
        report.setReportNumber(nextReportNumber);

        Report savedReport = reportRepository.save(report);
        return reportMapper.toDTO(savedReport);
    }

    @Override
    public List<ReportDTO> getAllReports() {
        return reportRepository.findAll().stream()
                .map(reportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByIds(Long departmentId, Long jobId, Long employeeId, Long accountId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new RuntimeException("Department not found");
        }
        if (!jobRepository.existsById(jobId)) {
            throw new RuntimeException("Job not found");
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found");
        }
        if (accountRepository.existsById(accountId)) {
            throw new RuntimeException("Account not found");
        }
        return reportRepository.findByDepartmentById(departmentId).stream()
                .map(reportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDTO getReportById(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        return reportMapper.toDTO(report);
    }

    @Override
    public ReportDTO updateReport(Long reportId, ReportDTO updatedReportDTO) {
        Report existingReport = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        Department department = departmentRepository.findById(updatedReportDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Job job = jobRepository.findById(updatedReportDTO.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        existingReport.setReportNumber(updatedReportDTO.getReportNumber());
        existingReport.setDepartment(department);
        existingReport.setJob(job);
        existingReport.setDepartmentIdentifier(updatedReportDTO.getDepartmentIdentifier());
        existingReport.setJobIdentifier(updatedReportDTO.getJobIdentifier());

        Report updatedReport = reportRepository.save(existingReport);
        return reportMapper.toDTO(updatedReport);
    }

    @Override
    public void deleteReport(Long reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new RuntimeException("Report not found");
        }
        reportRepository.deleteById(reportId);
    }

    @Override
    public void deleteReportsByIds(Long departmentId, Long jobId, Long employeeId, Long accountId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new RuntimeException("Department not found");
        }
        if (!jobRepository.existsById(jobId)) {
            throw new RuntimeException("Job not found");
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found");
        }
        if (accountRepository.existsById(accountId)) {
            throw new RuntimeException("Account not found");
        }
        List<Report> reports = reportRepository.findByDepartmentById(departmentId);
        reportRepository.deleteAll(reports);
    }
}