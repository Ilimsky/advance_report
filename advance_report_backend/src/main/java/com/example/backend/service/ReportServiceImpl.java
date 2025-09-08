package com.example.backend.service;

import com.example.backend.auth.CustomUserDetails;
import com.example.backend.dto.ReportDTO;
import com.example.backend.exception.EntityNotFoundException;
import com.example.backend.mapper.ReportMapper;
import com.example.backend.model.*;
import com.example.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);


    public ReportServiceImpl(ReportRepository reportRepository,
                             DepartmentRepository departmentRepository,
                             JobRepository jobRepository,
                             EmployeeRepository employeeRepository,
                             AccountRepository accountRepository,
                             ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.departmentRepository = departmentRepository;
        this.jobRepository = jobRepository;
        this.employeeRepository = employeeRepository;
        this.accountRepository = accountRepository;
        this.reportMapper = reportMapper;
    }

    private boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    private boolean isSuperAdmin() {
        return hasRole("SUPERADMIN");
    }

    private boolean isAdmin() {
        return hasRole("ADMIN") || isSuperAdmin();
    }

    private boolean isUser() {
        return hasRole("USER");
    }

    private Authentication getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("Authentication object: {}", auth);
        return auth;
    }

    private CustomUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            logger.debug("Retrieved user: {}, departmentId: {}",
                    userDetails.getUsername(), userDetails.getDepartmentId());
            return userDetails;
        }
        logger.warn("No CustomUserDetails found in authentication principal");
        return null;
    }

    private void checkUserAccessToDepartment(Long departmentId) {
        if (isUser()) {
            CustomUserDetails user = getCurrentUser();
            logger.debug("Checking user access: user departmentId={}, target departmentId={}",
                    user != null ? user.getDepartmentId() : "null", departmentId);

            if (user != null && !departmentId.equals(user.getDepartmentId())) {
                throw new AccessDeniedException("Доступ запрещён: можно работать только с отчетами своего филиала");
            }
        }
    }

    @Override
    public List<ReportDTO> getAllReports() {
        logger.info("getAllReports called");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("Current authentication: {}", auth);

        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            logger.debug("User details: username={}, departmentId={}, authorities={}",
                    userDetails.getUsername(), userDetails.getDepartmentId(), userDetails.getAuthorities());
        }

        if (isSuperAdmin() || isAdmin()) {
            logger.info("Admin/SuperAdmin role detected, returning all reports");
            return reportRepository.findAll()
                    .stream()
                    .map(reportMapper::toDTO)
                    .collect(Collectors.toList());
        } else if (isUser()) {
            logger.info("User role detected, filtering by department");
            CustomUserDetails user = getCurrentUser();
            if (user != null && user.getDepartmentId() != null) {
                logger.debug("Filtering reports for departmentId: {}", user.getDepartmentId());
                List<Report> reports = reportRepository.findByDepartmentById(user.getDepartmentId());
                logger.debug("Found {} reports for department {}", reports.size(), user.getDepartmentId());
                return reports.stream()
                        .map(reportMapper::toDTO)
                        .collect(Collectors.toList());
            } else {
                logger.warn("User has no department ID, returning empty list. User: {}", user);
                return Collections.emptyList();
            }
        }

        logger.warn("User has no valid roles, returning empty list");
        return Collections.emptyList();
    }

    @Override
    public ReportDTO getReportById(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Report", reportId));

        logger.debug("Getting report {} for department {}", reportId,
                report.getDepartment() != null ? report.getDepartment().getId() : "null");

        // SUPERADMIN и ADMIN имеют доступ ко всем отчетам
        if (isSuperAdmin() || isAdmin()) {
            logger.debug("Admin/SuperAdmin access granted to report {}", reportId);
        } else if (isUser()) {
            // USER может access только отчеты своего филиала
            checkUserAccessToDepartment(report.getDepartment().getId());
            logger.debug("User access granted to report {}", reportId);
        } else {
            logger.warn("User has no valid roles, access denied to report {}", reportId);
            throw new AccessDeniedException("Доступ запрещен");
        }

        return reportMapper.toDTO(report);
    }

    @Override
    public List<ReportDTO> getReportsByDepartmentId(Long departmentId) {
        checkUserAccessToDepartment(departmentId);

        return reportRepository.findByDepartmentById(departmentId)
                .stream()
                .map(reportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByIds(Long departmentId, Long jobId, Long employeeId, Long accountId) {
        checkUserAccessToDepartment(departmentId);

        // Проверка существования сущностей
        if (!departmentRepository.existsById(departmentId)) throw new EntityNotFoundException("Department", departmentId);
        if (!jobRepository.existsById(jobId)) throw new EntityNotFoundException("Job", jobId);
        if (!employeeRepository.existsById(employeeId)) throw new EntityNotFoundException("Employee", employeeId);
        if (!accountRepository.existsById(accountId)) throw new EntityNotFoundException("Account", accountId);

        // Берём отчёты только по филиалу
        return reportRepository.findByDepartmentById(departmentId)
                .stream()
                .map(reportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        if (isUser()) {
            checkUserAccessToDepartment(reportDTO.getDepartmentId());
        }

        Department department = departmentRepository.findById(reportDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department", reportDTO.getDepartmentId()));
        Job job = jobRepository.findById(reportDTO.getJobId())
                .orElseThrow(() -> new EntityNotFoundException("Job", reportDTO.getJobId()));
        Employee employee = employeeRepository.findById(reportDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", reportDTO.getEmployeeId()));
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
    public ReportDTO updateReport(Long reportId, ReportDTO updatedReportDTO) {
        if (!isAdmin() && !isSuperAdmin()) {
            throw new AccessDeniedException("Только администраторы могут редактировать отчеты");
        }

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
        if (!isSuperAdmin()) {
            throw new AccessDeniedException("Только супер-администраторы могут удалять отчеты");
        }

        if (!reportRepository.existsById(reportId)) {
            throw new EntityNotFoundException("Report", reportId);
        }
        reportRepository.deleteById(reportId);
    }

    @Override
    public void deleteReportsByIds(Long departmentId, Long jobId, Long employeeId, Long accountId) {
        if (!isSuperAdmin()) {
            throw new AccessDeniedException("Только супер-администраторы могут удалять отчеты");
        }

        if (!departmentRepository.existsById(departmentId)) throw new EntityNotFoundException("Department", departmentId);
        if (!jobRepository.existsById(jobId)) throw new EntityNotFoundException("Job", jobId);
        if (!employeeRepository.existsById(employeeId)) throw new EntityNotFoundException("Employee", employeeId);
        if (!accountRepository.existsById(accountId)) throw new EntityNotFoundException("Account", accountId);

        List<Report> reports = reportRepository.findByDepartmentById(departmentId);
        reportRepository.deleteAll(reports);
    }
}
