package com.example.reportservice.service;

import com.example.departmentservice.dto.DepartmentDTO;
import com.example.departmentservice.entity.Department;
import com.example.departmentservice.repo.DepartmentRepository;
import com.example.reportservice.entities.Report;
import com.example.reportservice.feign.DepartmentServiceClient;
import com.example.reportservice.repo.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentServiceClient departmentServiceClient;




    public Report createReport(Long departmentId) {
        DepartmentDTO departmentDTO = departmentServiceClient.getDepartment(departmentId);

        if (departmentDTO == null) {
            throw new RuntimeException("Department not found");
        }

        List<Report> reports = reportRepository.findByDepartmentIdentifier(departmentId);
        int nextReportNumber = reports.size() + 1;

        Report report = new Report();
        report.setReportNumber(nextReportNumber);
        report.setDepartmentIdentifier(departmentDTO.getId());

        return reportRepository.save(report);
    }



    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    // Получение отчетов по ID отдела
//    public List<Report> getReportsByDepartmentId(Long departmentId) {
//        if (!departmentRepository.existsById(departmentId)) {
//            throw new RuntimeException("Department not found");
//        }
//        return reportRepository.findByDepartmentId(departmentId);
//    }

    // Получение отчета по ID
    public Report getReportById(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    // Обновление отчета
//    public Report updateReport(Long reportId, Report updatedReport) {
//        Report existingReport = reportRepository.findById(reportId)
//                .orElseThrow(() -> new RuntimeException("Report not found"));
//
//        // Обновляем поля
//        existingReport.setReportNumber(updatedReport.getReportNumber());
//        existingReport.setDepartment(updatedReport.getDepartment());
//        existingReport.setDepartmentIdentifier(updatedReport.getDepartmentIdentifier());
//
//        return reportRepository.save(existingReport);
//    }

    // Удаление отчета
    public void deleteReport(Long reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new RuntimeException("Report not found");
        }
        reportRepository.deleteById(reportId);
    }

    // Удаление всех отчетов по ID отдела
//    public void deleteReportsByDepartmentId(Long departmentId) {
//        if (!departmentRepository.existsById(departmentId)) {
//            throw new RuntimeException("Department not found");
//        }
//        List<Report> reports = reportRepository.findByDepartmentId(departmentId);
//        reportRepository.deleteAll(reports);
//    }


}