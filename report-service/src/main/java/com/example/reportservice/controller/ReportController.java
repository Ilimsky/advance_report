package com.example.reportservice.controller;

import com.example.reportservice.dto.ReportDTO;
import com.example.reportservice.exception.ReportNotFoundException;
import com.example.reportservice.service.ReportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*")
public class ReportController {
    private final ReportServiceImpl reportServiceImpl;
    private final Map<String, Integer> branchCounters = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);


    @Autowired
    public ReportController(ReportServiceImpl reportServiceImpl){
        this.reportServiceImpl = reportServiceImpl;
    }

    @GetMapping("/counter/{branchName}")
    public ResponseEntity<Integer> getCounter(@PathVariable String branchName) {
        int counter = branchCounters.getOrDefault(branchName, 0);
        return ResponseEntity.ok(counter);
    }

    @PostMapping("/report")
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO) {
        // Логируем полученный sequenceNumber
        logger.debug("Sequence Number: {}", reportDTO.getSequenceNumber());

        // Разделяем sequenceNumber по "/"
        String[] parts = reportDTO.getSequenceNumber().split("/");

        // Проверка на корректность формата
        if (parts.length == 2) {
            try {
                // Присваиваем branchCounter значение из первого компонента строки
                int branchCounter = Integer.parseInt(parts[0]);
                reportDTO.setBranchCounter(branchCounter);

                // Извлекаем название филиала для обработки (если нужно)
                String branchName = parts[1];

                // Увеличиваем счётчик для филиала (если это необходимо)
                int updatedCounter = branchCounters.getOrDefault(branchName, 0) + 1;
                branchCounters.put(branchName, updatedCounter);

                // Перезаписываем sequenceNumber на основе нового счётчика
                reportDTO.setSequenceNumber(updatedCounter + "/" + branchName);

                // Сохраняем отчёт
                ReportDTO createdReportDTO = reportServiceImpl.createReport(reportDTO);

                return ResponseEntity.ok(createdReportDTO);
            } catch (NumberFormatException e) {
                // Логируем ошибку, если первый компонент не число
                logger.warn("Invalid sequence number format, first part is not a number: {}", reportDTO.getSequenceNumber());
                return ResponseEntity.badRequest().body(null);
            }
        } else {
            // Логируем предупреждение, если формат некорректен
            logger.warn("Invalid sequence number format: {}", reportDTO.getSequenceNumber());

            // Возвращаем ошибку клиенту
            return ResponseEntity.badRequest().body(null);
        }
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
            // Сначала получаем существующий отчет
            ReportDTO existingReport = reportServiceImpl.getReportById(id).orElseThrow(() -> new RuntimeException("Report not found"));

            // Обновляем только необходимые поля
            existingReport.setSequenceNumber(reportDTO.getSequenceNumber());
            existingReport.setBranchCounter(reportDTO.getBranchCounter()); // Обновление поля branchCounter

            // Сохраняем обновленный отчет
            ReportDTO updatedReportDTO = reportServiceImpl.updateReport(id, existingReport);
            return ResponseEntity.ok(updatedReportDTO);
        } catch(ReportNotFoundException e) {
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