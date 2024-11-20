package com.example.departmentservice.service;


import com.example.departmentservice.dto.ReportDTO;

import java.util.List;
import java.util.Optional;

public interface ReportService {

    ReportDTO createReport(ReportDTO reportDTO);

    List<ReportDTO> getAllReports();

    Optional<ReportDTO> getReportById(Long id);

    ReportDTO updateReport(Long id, ReportDTO reportDTO);

    void delete(Long id);
}
