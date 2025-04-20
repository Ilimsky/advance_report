package com.example.backend.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportServiceImpl reportServiceImpl;

    @Autowired
    public ReportController(ReportServiceImpl reportServiceImpl) {
        this.reportServiceImpl = reportServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        List<ReportDTO> reports = reportServiceImpl.getAllReports();
        return ResponseEntity.ok(reports);
    }


    @PostMapping
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO) {
        ReportDTO createdReport = reportServiceImpl.createReport(reportDTO);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    @GetMapping("/department/{departmentId}/job/{jobId}/employee/{employeeId}/account/{accountId}")
    public ResponseEntity<List<ReportDTO>> getReportsByIds(@PathVariable Long departmentId,
                                                           @PathVariable Long jobId,
                                                           @PathVariable Long employeeId,
                                                           @PathVariable Long accountId) {
        List<ReportDTO> reportsByIds = reportServiceImpl.getReportsByIds(departmentId, jobId, employeeId, accountId);
        return ResponseEntity.ok(reportsByIds);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long reportId) {
        Optional<ReportDTO> reportById = Optional.ofNullable(reportServiceImpl.getReportById(reportId));
        return reportById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<ReportDTO> updateReport(@PathVariable Long reportId, @RequestBody ReportDTO updatedReportDTO) {
        ReportDTO updatedReport = reportServiceImpl.updateReport(reportId, updatedReportDTO);
        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long reportId) {
        reportServiceImpl.deleteReport(reportId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/department/{departmentId}/job/{jobId}/employee/{employeeId}/account/{accountId}")
    public ResponseEntity<Void> deleteReportsByIds(@PathVariable Long departmentId,
                                                   @PathVariable Long jobId,
                                                   @PathVariable Long employeeId,
                                                   @PathVariable Long accountId) {
        reportServiceImpl.deleteReportsByIds(departmentId, jobId, employeeId, accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}