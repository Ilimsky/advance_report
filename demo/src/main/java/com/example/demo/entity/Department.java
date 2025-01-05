package com.example.demo.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Используем автоинкремент для Long
    private Long id;  // Используем Long как уникальный идентификатор

    private String name;  // Название департамента

    private LocalDateTime lastModified;  // Дата и время последнего изменения

    private boolean synced = false;  // Флаг синхронизации с сервером

    private boolean newDepartment = false;  // Флаг для нового департамента

    private boolean modified = false;  // Флаг для изменений департамента
}