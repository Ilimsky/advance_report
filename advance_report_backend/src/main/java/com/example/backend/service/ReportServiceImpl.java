package com.example.backend.service;

import com.example.backend.auth.CustomUserDetails;
import com.example.backend.dto.ReportDTO;
import com.example.backend.dto.UserDepartmentBindingDTO;
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
    private final UserDepartmentBindingService bindingService;
    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    public ReportServiceImpl(ReportRepository reportRepository,
                             DepartmentRepository departmentRepository,
                             JobRepository jobRepository,
                             EmployeeRepository employeeRepository,
                             AccountRepository accountRepository,
                             ReportMapper reportMapper,
                             UserDepartmentBindingService bindingService) {
        this.reportRepository = reportRepository;
        this.departmentRepository = departmentRepository;
        this.jobRepository = jobRepository;
        this.employeeRepository = employeeRepository;
        this.accountRepository = accountRepository;
        this.reportMapper = reportMapper;
        this.bindingService = bindingService;
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

    private CustomUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            logger.debug("Retrieved user: {}",
                    userDetails.getUsername());
            return userDetails;
        }
        logger.warn("No CustomUserDetails found in authentication principal");
        return null;
    }

    private void checkUserAccessToDepartment(Long departmentId) {
        if (isUser()) {
            Long userDepartmentId = getUserDepartmentId();
            if (userDepartmentId == null || !userDepartmentId.equals(departmentId)) {
                throw new AccessDeniedException("Доступ запрещён: можно работать только с отчетами своего филиала");
            }
        }
    }

    @Override
    public List<ReportDTO> getAllReports() {
        logger.info("getAllReports called");

        if (isSuperAdmin() || isAdmin()) {
            logger.info("Admin/SuperAdmin role detected, returning all reports");
            return reportRepository.findAll()
                    .stream()
                    .map(reportMapper::toDTO)
                    .collect(Collectors.toList());
        } else if (isUser()) {
            logger.info("User role detected, filtering by department");
            Long userDepartmentId = getUserDepartmentId();
            if (userDepartmentId == null) {
                return Collections.emptyList();
            }
            return reportRepository.findByDepartmentById(userDepartmentId)
                    .stream()
                    .map(reportMapper::toDTO)
                    .collect(Collectors.toList());
        }

        logger.warn("User has no valid roles, returning empty list");
        return Collections.emptyList();
    }

    @Override
    public ReportDTO getReportById(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Report", reportId));

        // SUPERADMIN и ADMIN имеют доступ ко всем отчетам
        if (isSuperAdmin() || isAdmin()) {
            logger.debug("Admin/SuperAdmin access granted to report {}", reportId);
        } else if (isUser()) {
            // USER может получить только отчеты своего филиала
            checkUserAccessToDepartment(report.getDepartment().getId());
            logger.debug("User access granted to report {}", reportId);
        } else {
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
        if (!departmentRepository.existsById(departmentId))
            throw new EntityNotFoundException("Department", departmentId);
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
            Long userDepartmentId = getUserDepartmentId();
            if (userDepartmentId == null || !userDepartmentId.equals(reportDTO.getDepartmentId())) {
                throw new AccessDeniedException("Пользователь может создавать отчёты только для своего филиала");
            }
        }

        Department department = departmentRepository.findById(reportDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department", reportDTO.getDepartmentId()));
        Job job = jobRepository.findById(reportDTO.getJobId())
                .orElseThrow(() -> new EntityNotFoundException("Job", reportDTO.getJobId()));
        Employee employee = employeeRepository.findById(reportDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", reportDTO.getEmployeeId()));
        Account account = accountRepository.findById(reportDTO.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account", reportDTO.getAccountId()));

        // Получаем год из даты получения денежных средств
        int year = reportDTO.getDateReceived().getYear();

        // Находим максимальный номер отчета для данного филиала и года
        Integer maxReportNumber = reportRepository.findMaxReportNumberByDepartmentAndYear(
                reportDTO.getDepartmentId(), year);

        // Следующий номер = максимальный + 1, или 1 если отчетов еще нет
        int nextReportNumber = (maxReportNumber != null) ? maxReportNumber + 1 : 1;

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

        if (!departmentRepository.existsById(departmentId))
            throw new EntityNotFoundException("Department", departmentId);
        if (!jobRepository.existsById(jobId)) throw new EntityNotFoundException("Job", jobId);
        if (!employeeRepository.existsById(employeeId)) throw new EntityNotFoundException("Employee", employeeId);
        if (!accountRepository.existsById(accountId)) throw new EntityNotFoundException("Account", accountId);

        List<Report> reports = reportRepository.findByDepartmentById(departmentId);
        reportRepository.deleteAll(reports);
    }

    private Long getUserDepartmentId() {
        if (isUser()) {
            CustomUserDetails user = getCurrentUser();
            if (user != null) {
                // Используем новый метод сервиса - он возвращает Long напрямую
                Long departmentId = bindingService.getUserDepartmentId(user.getId());
                if (departmentId == null) {
                    throw new AccessDeniedException("Пользователь не привязан к филиалу");
                }
                return departmentId;
            }
        }
        return null; // Для ADMIN/SUPERADMIN не ограничиваем
    }

    @Override
    public List<ReportDTO> getReportsByDepartmentIdAndYear(Long departmentId, int year) {
        checkUserAccessToDepartment(departmentId);

        return reportRepository.findByDepartmentIdAndYear(departmentId, year)
                .stream()
                .map(reportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getAllReportsByYear(int year) {
        logger.info("getAllReportsByYear called for year: {}", year);

        if (isSuperAdmin() || isAdmin()) {
            logger.info("Admin/SuperAdmin role detected, returning all reports for year {}", year);
            return reportRepository.findAll()
                    .stream()
                    .filter(report -> report.getDateReceived().getYear() == year)
                    .map(reportMapper::toDTO)
                    .collect(Collectors.toList());
        } else if (isUser()) {
            logger.info("User role detected, filtering by department and year {}", year);
            Long userDepartmentId = getUserDepartmentId();
            if (userDepartmentId == null) {
                return Collections.emptyList();
            }
            return reportRepository.findByDepartmentIdAndYear(userDepartmentId, year)
                    .stream()
                    .map(reportMapper::toDTO)
                    .collect(Collectors.toList());
        }

        logger.warn("User has no valid roles, returning empty list");
        return Collections.emptyList();
    }
}
