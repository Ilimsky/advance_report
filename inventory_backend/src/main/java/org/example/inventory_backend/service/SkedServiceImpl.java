package org.example.inventory_backend.service;

import com.github.dockerjava.api.exception.ConflictException;
import jakarta.transaction.Transactional;
import org.example.inventory_backend.SkedWebSocketService;
import org.example.inventory_backend.dto.SkedDTO;
import org.example.inventory_backend.exception.EntityNotFoundException;
import org.example.inventory_backend.mapper.SkedMapper;
import org.example.inventory_backend.model.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.example.inventory_backend.repository.SkedRepository;
import org.example.inventory_backend.repository.DepartmentRepository;
import org.example.inventory_backend.repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkedServiceImpl implements SkedService {
    private final SkedRepository skedRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final SkedMapper skedMapper;

    private final SkedWebSocketService webSocketService;


    public SkedServiceImpl(SkedRepository skedRepository, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, SkedMapper skedMapper, SkedWebSocketService webSocketService) {
        this.skedRepository = skedRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.skedMapper = skedMapper;
        this.webSocketService = webSocketService;
    }

    @Override
//    @Cacheable(value = "pagedSkeds", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<SkedDTO> getAllSkedsPaged(Pageable pageable) {
        long start = System.currentTimeMillis();
        Page<SkedDTO> result = skedRepository.findAll(pageable).map(skedMapper::toDTO);
        System.out.println("⏱ Запрос занял: " + (System.currentTimeMillis() - start) + " мс");
        return result;
    }

    @Override
//    @Cacheable(value = "pagedSkedsByDepartment", key = "#departmentId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<SkedDTO> getSkedsByDepartmentIdPaged(Long departmentId, Pageable pageable) {
        return skedRepository.findByDepartment_Id(departmentId, pageable)
                .map(skedMapper::toDTO);
    }

    @Override
    @CacheEvict(value = {"skedsById", "skedsByDepartmentId", "allSkeds"}, allEntries = true)
    @Transactional
    public SkedDTO createSked(SkedDTO skedDTO) {
        Department department = departmentRepository.findById(skedDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department", skedDTO.getDepartmentId()));
        Employee employee = employeeRepository.findById(skedDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", skedDTO.getEmployeeId()));

        // Получаем последний номер в этом отделе
        List<Sked> existingSkeds = skedRepository.findByDepartmentById(skedDTO.getDepartmentId());
        int nextNumber = existingSkeds.isEmpty() ? 1 : getNextSkedNumber(existingSkeds) + 1;

        // Форматируем номер: "IT/000001" (вместо "16/000001")
        String formattedSkedNumber = String.format("%s/%04d", department.getName(), nextNumber);

        // Проверяем, не существует ли уже такой номер
        if (skedRepository.existsBySkedNumberAndDepartment_Id(formattedSkedNumber, skedDTO.getDepartmentId())) {
            throw new ConflictException("Инвентарный номер " + formattedSkedNumber + " уже существует в этом отделе");
        }

        // Проверяем, не превышен ли лимит (9999)
        if (nextNumber > 9_999) {
            throw new IllegalStateException("Достигнут максимальный номер (9999) для отдела");
        }

        // Создаем и сохраняем запись
        Sked sked = skedMapper.toEntity(skedDTO, department, employee);
        sked.setSkedNumber(formattedSkedNumber);

        Sked savedSked = skedRepository.save(sked);
        return skedMapper.toDTO(savedSked);
    }

    // Вспомогательный метод для извлечения последнего номера
    private int getNextSkedNumber(List<Sked> skeds) {
        return skeds.stream()
                .map(s -> s.getSkedNumber().split("/")[1]) // извлекаем "000001" из "IT/000001"
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
    }

    @Override
    @Cacheable("allSkeds")
    public List<SkedDTO> getAllSkeds() {
        return skedRepository.findAll().stream()
                .map(skedMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "skedsByDepartmentId", key = "#departmentId")
    public List<SkedDTO> getSkedsByDepartmentId(Long departmentId) {
        return skedRepository.findByDepartmentById(departmentId)
                .stream()
                .map(skedMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "skedsById", key = "#skedId")
    public SkedDTO getSkedById(Long skedId) {
        Sked sked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));
        return skedMapper.toDTO(sked);
    }

    @Override
    @CacheEvict(value = {"skedsById", "skedsByDepartmentId", "allSkeds"}, allEntries = true)
    @Transactional
    public SkedDTO updateSked(Long skedId, SkedDTO updatedSkedDTO) {
        Sked existingSked = skedRepository.findById(skedId)
                .orElseThrow(() -> new EntityNotFoundException("Sked", skedId));

        Department department = departmentRepository.findById(updatedSkedDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department", updatedSkedDTO.getDepartmentId()));
        Employee employee = employeeRepository.findById(updatedSkedDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", updatedSkedDTO.getEmployeeId()));

        // Обновление базовых полей
        existingSked.setDepartment(department);
        existingSked.setEmployee(employee);
        existingSked.setDepartmentIdentifier(updatedSkedDTO.getDepartmentIdentifier());
        existingSked.setEmployeeIdentifier(updatedSkedDTO.getEmployeeIdentifier());

        existingSked.setAssetCategory(updatedSkedDTO.getAssetCategory());
        existingSked.setDateReceived(updatedSkedDTO.getDateReceived());
        existingSked.setSkedNumber(updatedSkedDTO.getSkedNumber());
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

        // Отправляем уведомление через WebSocket
        webSocketService.notifySkedUpdate(result);
        return result;
    }

    @Override
    @CacheEvict(value = {"skedsById", "skedsByDepartmentId", "allSkeds"}, allEntries = true)
    @Transactional
    public void deleteSked(Long skedId) {
        if (!skedRepository.existsById(skedId)) {
            throw new EntityNotFoundException("Sked", skedId);
        }
        skedRepository.deleteById(skedId);
    }
}