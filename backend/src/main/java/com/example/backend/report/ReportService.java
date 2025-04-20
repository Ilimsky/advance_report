package com.example.backend.report;

import java.util.List;

public interface ReportService {
    ReportDTO createReport(ReportDTO reportDTO);

    List<ReportDTO> getAllReports();

    List<ReportDTO> getReportsByIds(Long departmentId, Long jobId, Long employeeId, Long accountId);

    ReportDTO getReportById(Long reportId);

    ReportDTO updateReport(Long reportId, ReportDTO updatedReportDTO);

    void deleteReport(Long reportId);

    void deleteReportsByIds(Long departmentId, Long jobId, Long employeeId, Long accountId);
}