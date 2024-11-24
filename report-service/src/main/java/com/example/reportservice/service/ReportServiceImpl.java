package com.example.reportservice.service;

import com.example.reportservice.dto.ReportDTO;
import com.example.reportservice.entities.Report;
import com.example.reportservice.mapper.ReportMapper;
import com.example.reportservice.repo.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        Report report = reportMapper.toEntity(reportDTO);
        Report savedReport = reportRepository.save(report);
        return reportMapper.toDTO(savedReport);
    }

    @Override
    public List<ReportDTO> getAllReports() {
        return reportRepository.findAll()
                .stream()
                .map(reportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReportDTO> getReportById(Long id) {
        return reportRepository.findById(id)
                .map(reportMapper::toDTO);
    }

    @Override
    public ReportDTO updateReport(Long id, ReportDTO reportDTO) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setSequenceNumber(reportDTO.getSequenceNumber());
        Report updatedReport = reportRepository.save(report);
        return reportMapper.toDTO(updatedReport);
    }

    @Override
    public void delete(Long id) {
        reportRepository.deleteById(id);
    }


}
