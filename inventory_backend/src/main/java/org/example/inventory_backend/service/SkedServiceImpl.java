package org.example.inventory_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.exception.ConflictException;
import jakarta.transaction.Transactional;
import org.example.inventory_backend.SkedWebSocketService;
import org.example.inventory_backend.auth.CustomUserDetails;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.example.inventory_backend.repository.SkedRepository;
import org.example.inventory_backend.repository.DepartmentRepository;
import org.example.inventory_backend.repository.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.Collections;
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
    private final UserDepartmentBindingService bindingService;

    public SkedServiceImpl(SkedRepository skedRepository,
                           DepartmentRepository departmentRepository,
                           EmployeeRepository employeeRepository,
                           SkedMapper skedMapper,
                           SkedWebSocketService webSocketService,
                           SkedHistoryRepository historyRepository,
                           ObjectMapper objectMapper,
                           UserDepartmentBindingService bindingService) {
        this.skedRepository = skedRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.skedMapper = skedMapper;
        this.webSocketService = webSocketService;
        this.historyRepository = historyRepository;
        this.objectMapper = objectMapper;
        this.bindingService = bindingService;
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
        if (isSuperAdmin() || isAdmin()) {
            return skedRepository.findAll(pageable).map(skedMapper::toDTO);
        } else if (isUser()) {
            Long userDepartmentId = getUserDepartmentId();
            if (userDepartmentId == null) {
                return Page.empty();
            }
            return skedRepository.findByDepartment_Id(userDepartmentId, pageable)
                    .map(skedMapper::toDTO);
        }
        return Page.empty();
    }

    @Override
    public Page<SkedDTO> getSkedsByDepartmentIdPaged(Long departmentId, Pageable pageable) {
        checkUserAccessToDepartment(departmentId);
        return skedRepository.findByDepartment_Id(departmentId, pageable)
                .map(skedMapper::toDTO);
    }

    // ======================== Create ============================
    @Override
    @Transactional
    public SkedDTO createSked(SkedDTO skedDTO) {
        if (isUser()) {
            Long userDepartmentId = getUserDepartmentId();
            if (userDepartmentId == null || !userDepartmentId.equals(skedDTO.getDepartmentId())) {
                throw new AccessDeniedException("Пользователь может создавать записи только для своего филиала");
            }
        }
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
        if (isSuperAdmin() || isAdmin()) {
            return skedRepository.findAll().stream()
                    .map(skedMapper::toDTO)
                    .collect(Collectors.toList());
        } else if (isUser()) {
            Long userDepartmentId = getUserDepartmentId();
            if (userDepartmentId == null) {
                return Collections.emptyList();
            }
            return skedRepository.findByDepartmentById(userDepartmentId)
                    .stream()
                    .map(skedMapper::toDTO)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<SkedDTO> getSkedsByDepartmentId(Long departmentId) {
        checkUserAccessToDepartment(departmentId);
        return skedRepository.findByDepartmentById(departmentId)
                .stream()
                .map(skedMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SkedDTO getSkedById(Long skedId) {
        Sked sked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));

        // SUPERADMIN и ADMIN имеют доступ ко всем записям
        if (isSuperAdmin() || isAdmin()) {
            // Доступ разрешен
        } else if (isUser()) {
            // USER может получить только записи своего филиала
            checkUserAccessToDepartment(sked.getDepartment().getId());
        } else {
            throw new AccessDeniedException("Доступ запрещен");
        }

        return skedMapper.toDTO(sked);
    }

    // ======================== Update (перемещение) ============================
    @Override
    @Transactional
    public SkedDTO updateSked(Long skedId, SkedDTO updatedSkedDTO) {
        if (!isAdmin() && !isSuperAdmin()) {
            throw new AccessDeniedException("Только администраторы могут редактировать записи");
        }
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
        if (!isSuperAdmin()) {
            throw new AccessDeniedException("Только супер-администраторы могут списывать записи");
        }

        Sked sked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));

        saveHistory(sked, "WRITE_OFF", reason);
        skedRepository.delete(sked);
    }

    // Новый метод для перемещения с историей
    // В SkedServiceImpl
    public SkedDTO transferSked(Long skedId, Long newDepartmentId, String reason) {
        if (!isAdmin() && !isSuperAdmin()) {
            throw new AccessDeniedException("Только администраторы могут перемещать записи");
        }
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
        if (!isSuperAdmin()) {
            throw new AccessDeniedException("Только супер-администраторы могут удалять записи");
        }

        Sked sked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));

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
        if (!isAdmin() && !isSuperAdmin()) {
            throw new AccessDeniedException("Только администраторы могут освобождать номера");
        }
        Sked sked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));

        // НЕ удаляем запись, а только освобождаем номер!
        sked.setNumberReleased(true);
        sked.setSkedNumber(null); // Очищаем номер

        skedRepository.save(sked);
    }

    // === ДОБАВЛЕННЫЕ МЕТОДЫ ПРОВЕРКИ ПРАВ ===
    private boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    private boolean isSuperAdmin() {
        return hasRole("SUPERADMIN");
    }

    private boolean isAdmin() {
        return hasRole("ADMIN") || isSuperAdmin();
    }

    private boolean isUser() {
        return hasRole("USER");
    }

    private CustomUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) auth.getPrincipal();
        }
        return null;
    }

    private Long getUserDepartmentId() {
        if (isUser()) {
            CustomUserDetails user = getCurrentUser();
            if (user != null) {
                // Используем тот же сервис, что и в ReportServiceImpl
                Long departmentId = bindingService.getUserDepartmentId(user.getId());
                if (departmentId == null) {
                    throw new AccessDeniedException("Пользователь не привязан к филиалу");
                }
                return departmentId;
            }
        }
        return null;
    }

    private void checkUserAccessToDepartment(Long departmentId) {
        if (isUser()) {
            Long userDepartmentId = getUserDepartmentId();
            if (userDepartmentId == null || !userDepartmentId.equals(departmentId)) {
                throw new AccessDeniedException("Доступ запрещён: можно работать только с данными своего филиала");
            }
        }
    }
}
