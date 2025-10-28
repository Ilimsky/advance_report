package com.example.backend.controller;

import com.example.backend.dto.ReportDTO;
import com.example.backend.exception.EntityNotFoundException;
import com.example.backend.service.ReportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {
    private final ReportServiceImpl reportServiceImpl;
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    public ReportController(ReportServiceImpl reportServiceImpl) {
        this.reportServiceImpl = reportServiceImpl;
    }

    @PutMapping("/{reportId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<ReportDTO> updateReport(@PathVariable Long reportId, @RequestBody ReportDTO updatedReportDTO) {
        ReportDTO updatedReport = reportServiceImpl.updateReport(reportId, updatedReportDTO);
        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        logger.info("GET /api/reports endpoint called");
        try {
            List<ReportDTO> reports = reportServiceImpl.getAllReports();
            logger.info("Successfully retrieved {} reports", reports.size());
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            logger.error("Error in getAllReports: {}", e.getMessage(), e);
            throw e;
        }
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO) {
        ReportDTO createdReport = reportServiceImpl.createReport(reportDTO);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    @GetMapping("/department/{departmentId}/job/{jobId}/employee/{employeeId}/account/{accountId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<ReportDTO>> getReportsByIds(@PathVariable Long departmentId,
                                                           @PathVariable Long jobId,
                                                           @PathVariable Long employeeId,
                                                           @PathVariable Long accountId) {
        List<ReportDTO> reportsByIds = reportServiceImpl.getReportsByIds(departmentId, jobId, employeeId, accountId);
        return ResponseEntity.ok(reportsByIds);
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<ReportDTO>> getReportsByDepartment(@PathVariable Long departmentId) {
        List<ReportDTO> reports = reportServiceImpl.getReportsByDepartmentId(departmentId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{reportId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long reportId) {
        logger.info("GET /api/reports/{} endpoint called", reportId);
        try {
            ReportDTO report = reportServiceImpl.getReportById(reportId);
            logger.info("Successfully retrieved report {}", reportId);
            return ResponseEntity.ok(report);
        } catch (EntityNotFoundException e) {
            logger.warn("Report {} not found", reportId);
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            logger.warn("Access denied to report {}: {}", reportId, e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            logger.error("Error getting report {}: {}", reportId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{reportId}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> deleteReport(@PathVariable Long reportId) {
        reportServiceImpl.deleteReport(reportId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/department/{departmentId}/job/{jobId}/employee/{employeeId}/account/{accountId}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> deleteReportsByIds(@PathVariable Long departmentId,
                                                   @PathVariable Long jobId,
                                                   @PathVariable Long employeeId,
                                                   @PathVariable Long accountId) {
        reportServiceImpl.deleteReportsByIds(departmentId, jobId, employeeId, accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/year/{year}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<ReportDTO>> getAllReportsByYear(@PathVariable int year) {
        logger.info("GET /api/reports/year/{} endpoint called", year);
        try {
            List<ReportDTO> reports = reportServiceImpl.getAllReportsByYear(year);
            logger.info("Successfully retrieved {} reports for year {}", reports.size(), year);
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            logger.error("Error in getAllReportsByYear: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/department/{departmentId}/year/{year}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<List<ReportDTO>> getReportsByDepartmentAndYear(
            @PathVariable Long departmentId,
            @PathVariable int year) {
        List<ReportDTO> reports = reportServiceImpl.getReportsByDepartmentIdAndYear(departmentId, year);
        return ResponseEntity.ok(reports);
    }
}