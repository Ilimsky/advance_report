package com.example.backend.service;

import com.example.backend.dto.BindingDTO;

import java.util.List;

public interface BindingService {
    // Создание новой привязки сотрудника к филиалу и должности
    BindingDTO createBinding(BindingDTO bindingDTO);

    // Получение всех привязок сотрудников
    List<BindingDTO> getAllBindings();

    // Получение привязок сотрудников по ID филиала, должности, сотрудника и счёта
    List<BindingDTO> getBindingsByIds(Long departmentId, Long jobId, Long employeeId);

    // Получение привязки по ID
    BindingDTO getBindingById(Long bindingId);

    // Обновление привязки сотрудника
    BindingDTO updateBinding(Long bindingId, BindingDTO updatedBindingDTO);

    // Удаление привязки по ID
    void deleteBinding(Long bindingId);

    // Удаление привязок по фильтрам (филиал, должность, сотрудник и счёт)
    void deleteBindingsByIds(Long departmentId, Long jobId, Long employeeId);
}
