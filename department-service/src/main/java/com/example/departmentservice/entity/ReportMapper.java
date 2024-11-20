package com.example.departmentservice.entity;

import com.example.departmentservice.dto.ReportDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportMapper {

    // Преобразование из сущности в DTO
    public ReportDTO toDTO(Report report) {
        if (report == null) {
            return null;
        }

        return new ReportDTO(
                report.getId(),
                report.getSequenceNumber(),
                report.getLastModified(),
                report.isSynced(),
                report.isNewReport(),
                report.isModified()
        );
    }

    // Преобразование из DTO в сущность
    public Report toEntity(ReportDTO reportDTO) {
        if (reportDTO == null) {
            return null;
        }

        Report report = new Report();
        report.setId(reportDTO.getId());
        report.setSequenceNumber(reportDTO.getSequenceNumber());
        report.setLastModified(reportDTO.getLastModified());
        report.setSynced(reportDTO.isSynced());
        report.setNewReport(reportDTO.isNewReport());
        report.setModified(reportDTO.isModified());

        return report;
    }

    // Преобразование из списка сущностей в список DTO
    public List<ReportDTO> toDTOList(List<Report> reports) {
        if (reports == null) {
            return null;
        }
        return reports.stream()
                .map(this::toDTO)  // Используем this для вызова нестатического метода
                .collect(Collectors.toList());
    }

    // Преобразование из списка DTO в список сущностей
    public List<Report> toEntityList(List<ReportDTO> reportDTOs) {
        if (reportDTOs == null) {
            return null;
        }
        return reportDTOs.stream()
                .map(this::toEntity)  // Используем this для вызова нестатического метода
                .collect(Collectors.toList());
    }
}