package com.example.reportservice.controller;

import com.example.reportservice.entities.Report;
import com.example.reportservice.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/{departmentId}")
    public Report createReport(@PathVariable Long departmentId) {
        return reportService.createReport(departmentId);
    }

    @GetMapping
    public List<Report> getAllReports() {
        System.out.println("GET /api/reports called");
        return reportService.getAllReports();
    }

    @GetMapping("/department/{departmentId}")
//    public List<Report> getReportsByDepartmentId(@PathVariable Long departmentId) {
//        return reportService.getReportsByDepartmentId(departmentId);
//    }

//    @GetMapping("/{reportId}")
//    public Report getReportById(@PathVariable Long reportId) {
//        return reportService.getReportById(reportId);
//    }

//    @PutMapping("/{reportId}")
//    public Report updateReport(@PathVariable Long reportId, @RequestBody Report updatedReport) {
//        return reportService.updateReport(reportId, updatedReport);
//    }

    @DeleteMapping("/{reportId}")
    public void deleteReport(@PathVariable Long reportId) {
        reportService.deleteReport(reportId);
    }

//    @DeleteMapping("/department/{departmentId}")
//    public void deleteReportsByDepartmentId(@PathVariable Long departmentId) {
//        reportService.deleteReportsByDepartmentId(departmentId);
//    }
}