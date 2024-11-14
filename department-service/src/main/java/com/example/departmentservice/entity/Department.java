package com.example.departmentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private LocalDateTime lastModified;  // Дата и время последнего изменения

    private boolean synced = false;  // Флаг синхронизации с сервером

    private boolean newDepartment = false;  // Флаг для нового департамента

    private boolean modified = false;  // Флаг для изменений департамента
}