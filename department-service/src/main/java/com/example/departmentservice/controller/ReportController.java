package com.example.departmentservice.controller;

import com.example.departmentservice.dto.ReportDTO;
import com.example.departmentservice.exception.ReportNotFoundException;
import com.example.departmentservice.service.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class ReportController {
    private final ReportServiceImpl reportServiceImpl;

    @Autowired
    public ReportController(ReportServiceImpl reportServiceImpl){
        this.reportServiceImpl = reportServiceImpl;
    }

    @PostMapping("/report")
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO) {
        ReportDTO createReportDTO = reportServiceImpl.createReport(reportDTO);
        return ResponseEntity.ok(createReportDTO);
    }

    @PostMapping("/reports")
    public ResponseEntity<List<ReportDTO>> createReports(@RequestBody List<ReportDTO> reportDTOs){
        List<ReportDTO> createReportDTOs = reportDTOs.stream()
        .map(reportServiceImpl::createReport)
        .collect(Collectors.toList());
        return ResponseEntity.ok(createReportDTOs);
    }

    @GetMapping("/reports")
    public ResponseEntity<List<ReportDTO>> getAllReports(){
        List<ReportDTO> reportDTOs = reportServiceImpl.getAllReports();
        return ResponseEntity.ok(reportDTOs);
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long id){
        Optional<ReportDTO> reportDTO = reportServiceImpl.getReportById(id);
        if(reportDTO.isPresent()){
            return ResponseEntity.ok(reportDTO.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/report/{id}")
    public ResponseEntity<ReportDTO> updateReport(@PathVariable Long id, @RequestBody ReportDTO reportDTO){
        try{
            ReportDTO updateReportDTO = reportServiceImpl.updateReport(id, reportDTO);
            return ResponseEntity.ok(updateReportDTO);
        }catch(ReportNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/report/{id}")
    public ResponseEntity<ReportDTO> deleteReportById(@PathVariable Long id){
        try{
            reportServiceImpl.delete(id);
            return ResponseEntity.noContent().build();
        }catch(ReportNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}