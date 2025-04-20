package com.example.backend.report;

import com.example.backend.account.Account;
import com.example.backend.account.AccountRepository;
import com.example.backend.department.Department;
import com.example.backend.department.DepartmentRepository;
import com.example.backend.employee.Employee;
import com.example.backend.employee.EmployeeRepository;
import com.example.backend.job.Job;
import com.example.backend.job.JobRepository;
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

        Employee employee = employeeRepository.findById(updatedReportDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Account account = accountRepository.findById(updatedReportDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Обновление всех полей
        existingReport.setReportNumber(updatedReportDTO.getReportNumber());
        existingReport.setDepartment(department);
        existingReport.setJob(job);
        existingReport.setEmployee(employee);
        existingReport.setAccount(account);

        existingReport.setDepartmentIdentifier(updatedReportDTO.getDepartmentIdentifier());
        existingReport.setJobIdentifier(updatedReportDTO.getJobIdentifier());
        existingReport.setEmployeeIdentifier(updatedReportDTO.getEmployeeIdentifier());
        existingReport.setAccountIdentifier(updatedReportDTO.getAccountIdentifier());

        existingReport.setDateReceived(updatedReportDTO.getDateReceived());
        existingReport.setAmountIssued(updatedReportDTO.getAmountIssued());
        existingReport.setDateApproved(updatedReportDTO.getDateApproved());
        existingReport.setPurpose(updatedReportDTO.getPurpose());
        existingReport.setRecognizedAmount(updatedReportDTO.getRecognizedAmount());
        existingReport.setComments(updatedReportDTO.getComments());

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