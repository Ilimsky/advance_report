package com.example.reportservice.mapper;

import com.example.reportservice.dto.ReportDTO;
import com.example.reportservice.entities.Report;
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
        System.out.println("Mapping report to DTO: " + report.getId() + ", BranchCounter: " + report.getBranchCounter());
        return new ReportDTO(
                report.getId(),
                report.getSequenceNumber(),
                report.getBranchCounter()
        );
    }


    // Преобразование из DTO в сущность
    public Report toEntity(ReportDTO reportDTO) {
        if (reportDTO == null) {
            return null;
        }

        // Логируем значения перед преобразованием
        System.out.println("Mapping ReportDTO to entity: " + reportDTO.getId() + ", " + reportDTO.getBranchCounter());

        Report report = new Report();
        report.setId(reportDTO.getId());
        report.setSequenceNumber(reportDTO.getSequenceNumber());
        report.setBranchCounter(reportDTO.getBranchCounter());  // Убедитесь, что это поле корректно маппится
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