package com.example.departmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    private Long id;  // Используем Long для идентификатора, как в сущности

    private String sequenceNumber;  // Название департамента

    private LocalDateTime lastModified;  // Дата и время последнего изменения

    private boolean synced;  // Флаг синхронизации с сервером

    private boolean newReport;  // Флаг для нового отчета

    private boolean modified;  // Флаг для изменений отчета
}
