package org.example.inventory_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.exception.ConflictException;
import jakarta.transaction.Transactional;
import org.example.inventory_backend.SkedWebSocketService;
import org.example.inventory_backend.dto.SkedDTO;
import org.example.inventory_backend.exception.EntityNotFoundException;
import org.example.inventory_backend.mapper.SkedMapper;
import org.example.inventory_backend.model.*;
import org.example.inventory_backend.repository.SkedHistoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.example.inventory_backend.repository.SkedRepository;
import org.example.inventory_backend.repository.DepartmentRepository;
import org.example.inventory_backend.repository.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkedServiceImpl implements SkedService {
    private final SkedRepository skedRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final SkedMapper skedMapper;
    private final SkedWebSocketService webSocketService;
    private final SkedHistoryRepository historyRepository;

    private final ObjectMapper objectMapper; // Для JSON сериализации

    public SkedServiceImpl(SkedRepository skedRepository,
                           DepartmentRepository departmentRepository,
                           EmployeeRepository employeeRepository,
                           SkedMapper skedMapper,
                           SkedWebSocketService webSocketService,
                           SkedHistoryRepository historyRepository,
                           ObjectMapper objectMapper) {
        this.skedRepository = skedRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.skedMapper = skedMapper;
        this.webSocketService = webSocketService;
        this.historyRepository = historyRepository;
        this.objectMapper = objectMapper;
    }

    private void saveHistory(Sked sked, String actionType, String reason) {
        try {
            SkedHistory history = new SkedHistory();
            history.setSked(sked);
            history.setActionType(actionType);
            history.setReason(reason);

            // Сериализуем текущее состояние в JSON
            String currentData = objectMapper.writeValueAsString(sked);
            history.setNewData(currentData);

            historyRepository.save(history);
        } catch (JsonProcessingException e) {
            System.out.println("Error saving history for sked {}" + sked.getId());
        }
    }

    private void saveTransferHistory(Sked sked, Department oldDepartment, Department newDepartment, String reason) {
        try {
            SkedHistory history = new SkedHistory();
            history.setSked(sked);
            history.setActionType("TRANSFER");
            history.setReason(reason);
            history.setPerformedBy("SYSTEM");

            // Сохраняем информацию о перемещении
            history.setPreviousData(String.format(
                    "{\"departmentId\": %d, \"departmentName\": \"%s\", \"skedNumber\": \"%s\"}",
                    oldDepartment.getId(),
                    oldDepartment.getName(),
                    sked.getSkedNumber()
            ));

            // Генерируем новый номер для нового отдела
            String newSkedNumber = generateNextNumber(newDepartment.getId(), newDepartment.getName());

            history.setNewData(String.format(
                    "{\"departmentId\": %d, \"departmentName\": \"%s\", \"skedNumber\": \"%s\"}",
                    newDepartment.getId(),
                    newDepartment.getName(),
                    newSkedNumber
            ));

            historyRepository.save(history);
        } catch (Exception e) {
            System.out.println("Error saving transfer history: " + e.getMessage());
        }
    }

    // ======================== Paged ============================
    @Override
    public Page<SkedDTO> getAllSkedsPaged(Pageable pageable) {
        return skedRepository.findAll(pageable).map(skedMapper::toDTO);
    }

    @Override
    public Page<SkedDTO> getSkedsByDepartmentIdPaged(Long departmentId, Pageable pageable) {
        return skedRepository.findByDepartment_Id(departmentId, pageable)
                .map(skedMapper::toDTO);
    }

    // ======================== Create ============================
    @Override
    @Transactional
    public SkedDTO createSked(SkedDTO skedDTO) {
        Department department = departmentRepository.findById(skedDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department", skedDTO.getDepartmentId()));
        Employee employee = employeeRepository.findById(skedDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", skedDTO.getEmployeeId()));

        String nextNumber = generateNextNumber(department.getId(), department.getName());

        Sked sked = skedMapper.toEntity(skedDTO, department, employee);
        sked.setSkedNumber(nextNumber);

        Sked savedSked = skedRepository.save(sked);
        saveHistory(savedSked, "CREATE", "Создание новой записи");
        return skedMapper.toDTO(savedSked);
    }

    // ======================== Get ============================
    @Override
    public List<SkedDTO> getAllSkeds() {
        return skedRepository.findAll().stream()
                .map(skedMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SkedDTO> getSkedsByDepartmentId(Long departmentId) {
        return skedRepository.findByDepartmentById(departmentId)
                .stream()
                .map(skedMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SkedDTO getSkedById(Long skedId) {
        Sked sked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));
        return skedMapper.toDTO(sked);
    }

    // ======================== Update (перемещение) ============================
    @Override
    @Transactional
    public SkedDTO updateSked(Long skedId, SkedDTO updatedSkedDTO) {
        Sked existingSked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));
        Sked oldSked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));

        try {
            String oldData = objectMapper.writeValueAsString(oldSked);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Long oldDepartmentId = existingSked.getDepartment().getId();
        Long newDepartmentId = updatedSkedDTO.getDepartmentId();

        Department newDepartment = departmentRepository.findById(newDepartmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department", newDepartmentId));
        Employee newEmployee = employeeRepository.findById(updatedSkedDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", updatedSkedDTO.getEmployeeId()));

        // Если филиал изменился, освободить номер в старом филиале
        if (!oldDepartmentId.equals(newDepartmentId)) {
            // просто очищаем номер в старом филиале
            existingSked.setSkedNumber(null);

            // присваиваем новый номер в новом филиале
            String nextNumber = generateNextNumber(newDepartmentId, newDepartment.getName());
            existingSked.setSkedNumber(nextNumber);
        }

        existingSked.setDepartment(newDepartment);
        existingSked.setEmployee(newEmployee);
        existingSked.setDepartmentIdentifier(updatedSkedDTO.getDepartmentIdentifier());
        existingSked.setEmployeeIdentifier(updatedSkedDTO.getEmployeeIdentifier());
        existingSked.setAssetCategory(updatedSkedDTO.getAssetCategory());
        existingSked.setDateReceived(updatedSkedDTO.getDateReceived());
        existingSked.setItemName(updatedSkedDTO.getItemName());
        existingSked.setSerialNumber(updatedSkedDTO.getSerialNumber());
        existingSked.setCount(updatedSkedDTO.getCount());
        existingSked.setMeasure(updatedSkedDTO.getMeasure());
        existingSked.setPrice(updatedSkedDTO.getPrice());
        existingSked.setPlace(updatedSkedDTO.getPlace());
        existingSked.setComments(updatedSkedDTO.getComments());
        existingSked.setAvailable(updatedSkedDTO.isAvailable());

        Sked updatedSked = skedRepository.save(existingSked);
        SkedDTO result = skedMapper.toDTO(updatedSked);

        webSocketService.notifySkedUpdate(result);
        return result;
    }

    public void writeOffSked(Long skedId, String reason) {
        Sked sked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));

        saveHistory(sked, "WRITE_OFF", reason);
        skedRepository.delete(sked);
    }

    // Новый метод для перемещения с историей
    // В SkedServiceImpl
    public SkedDTO transferSked(Long skedId, Long newDepartmentId, String reason) {
        Sked sked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));

        Department oldDepartment = sked.getDepartment();
        Department newDepartment = departmentRepository.findById(newDepartmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department", newDepartmentId));

        // Сохраняем историю перед изменением
        saveTransferHistory(sked, oldDepartment, newDepartment, reason);

        // Обновляем запись
        sked.setDepartment(newDepartment);
        sked.setSkedNumber(generateNextNumber(newDepartmentId, newDepartment.getName()));
        sked.setComments("Перемещено из " + oldDepartment.getName() + ". " + sked.getComments());

        Sked updatedSked = skedRepository.save(sked);
        return skedMapper.toDTO(updatedSked);
    }

    // ======================== Delete (списание) ============================
    @Override
    @Transactional
    public void deleteSked(Long skedId) {
        Sked sked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));

        // При удалении освобождаем номер (по сути, удаляя запись)
        skedRepository.delete(sked);
    }

    // ======================== Helper ============================
    private String generateNextNumber(Long departmentId, String departmentName) {
        List<String> numbers = skedRepository.findAllActiveNumbersByDepartment(departmentId);

        // Преобразуем в список целых номеров
        List<Integer> existingNumbers = numbers.stream()
                .filter(num -> num != null && num.contains("/"))
                .map(num -> Integer.parseInt(num.split("/")[1]))
                .sorted()
                .collect(Collectors.toList());

        int candidate = 1;
        for (int num : existingNumbers) {
            if (num == candidate) {
                candidate++;
            } else if (num > candidate) {
                break;
            }
        }

        if (candidate > 9999) {
            throw new IllegalStateException("Достигнут максимальный номер (9999) для отдела");
        }

        return String.format("%s/%04d", departmentName, candidate);
    }

    @Transactional
    public void releaseSkedNumber(Long skedId) {
        Sked sked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));

        // НЕ удаляем запись, а только освобождаем номер!
        sked.setNumberReleased(true);
        sked.setSkedNumber(null); // Очищаем номер

        skedRepository.save(sked);
    }
}
