package com.example.reportservice.service;


import com.example.reportservice.dto.ReportDTO;

import java.util.List;
import java.util.Optional;

public interface ReportService {

    ReportDTO createReport(ReportDTO reportDTO);

    List<ReportDTO> getAllReports();

    Optional<ReportDTO> getReportById(Long id);

    ReportDTO updateReport(Long id, ReportDTO reportDTO);

    void delete(Long id);
}
