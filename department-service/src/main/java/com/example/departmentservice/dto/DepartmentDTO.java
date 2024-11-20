package com.example.departmentservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentDTO {
    private Long id;  // Используем Long для идентификатора, как в сущности

    private String name;  // Название департамента

    private LocalDateTime lastModified;  // Дата и время последнего изменения

    private boolean synced;  // Флаг синхронизации с сервером

    private boolean newDepartment;  // Флаг для нового департамента

    private boolean modified;  // Флаг для изменений департамента
}
