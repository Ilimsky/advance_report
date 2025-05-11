package com.example.backend.service;

import com.example.backend.dto.ReportDTO;
import com.example.backend.exception.EntityNotFoundException;
import com.example.backend.mapper.ReportMapper;
import com.example.backend.model.*;
import com.example.backend.repository.*;
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
                .orElseThrow(() -> new EntityNotFoundException("Department", reportDTO.getDepartmentId()));
        Job job = jobRepository.findById(reportDTO.getJobId())
                .orElseThrow(() -> new EntityNotFoundException("Job", reportDTO.getJobId()));
        Employee employee = employeeRepository.findById(reportDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", reportDTO.getDepartmentId()));
        Account account = accountRepository.findById(reportDTO.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account", reportDTO.getAccountId()));

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
            throw new EntityNotFoundException("Department", departmentId);
        }
        if (!jobRepository.existsById(jobId)) {
            throw new EntityNotFoundException("Job", jobId);
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee", employeeId);
        }
        if (accountRepository.existsById(accountId)) {
            throw new EntityNotFoundException("Account", accountId);
        }
        return reportRepository.findByDepartmentById(departmentId).stream()
                .map(reportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDTO getReportById(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Report", reportId));
        return reportMapper.toDTO(report);
    }

    @Override
    public ReportDTO updateReport(Long reportId, ReportDTO updatedReportDTO) {
        Report existingReport = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Report", reportId));

        Department department = departmentRepository.findById(updatedReportDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department", updatedReportDTO.getDepartmentId()));

        Job job = jobRepository.findById(updatedReportDTO.getJobId())
                .orElseThrow(() -> new EntityNotFoundException("Job", updatedReportDTO.getJobId()));

        Employee employee = employeeRepository.findById(updatedReportDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", updatedReportDTO.getEmployeeId()));

        Account account = accountRepository.findById(updatedReportDTO.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account", updatedReportDTO.getAccountId()));

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
            throw new EntityNotFoundException("Report", reportId);
        }
        reportRepository.deleteById(reportId);
    }

    @Override
    public void deleteReportsByIds(Long departmentId, Long jobId, Long employeeId, Long accountId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department", departmentId);
        }
        if (!jobRepository.existsById(jobId)) {
            throw new EntityNotFoundException("Job", jobId);
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee", employeeId);
        }
        if (accountRepository.existsById(accountId)) {
            throw new EntityNotFoundException("Account", accountId);
        }
        List<Report> reports = reportRepository.findByDepartmentById(departmentId);
        reportRepository.deleteAll(reports);
    }
}