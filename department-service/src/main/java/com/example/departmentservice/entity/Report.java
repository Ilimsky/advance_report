package com.example.departmentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sequenceNumber;

    private LocalDateTime lastModified;  // Дата и время последнего изменения

    private boolean synced = false;  // Флаг синхронизации с сервером

    private boolean newReport = false;  // Флаг для нового отчета

    private boolean modified = false;  // Флаг для изменений отчета
}
