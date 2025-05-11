package org.example.revizor.service;

import lombok.extern.slf4j.Slf4j;
import org.example.revizor.dto.AuditDTO;
import org.example.revizor.exception.*;
import org.example.revizor.mapper.AuditMapper;
import org.example.revizor.model.Audit;
import org.example.revizor.model.Department;
import org.example.revizor.model.Employee;
import org.example.revizor.model.Revizor;
import org.example.revizor.repository.AuditRepository;
import org.example.revizor.repository.DepartmentRepository;
import org.example.revizor.repository.EmployeeRepository;
import org.example.revizor.repository.RevizorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class AuditServiceImpl  implements AuditService {

    private final AuditRepository auditRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final RevizorRepository revizorRepository;
    private final AuditMapper auditMapper;

    public AuditServiceImpl(AuditRepository auditRepository, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, RevizorRepository revizorRepository, AuditMapper auditMapper) {
        this.auditRepository = auditRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.revizorRepository = revizorRepository;
        this.auditMapper = auditMapper;
    }
    @Override
    public AuditDTO createAudit(AuditDTO auditDTO) {
        Department department = departmentRepository.findById(auditDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department", auditDTO.getDepartmentId()));
        Employee employee = employeeRepository.findById(auditDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", auditDTO.getEmployeeId()));  // Исправлено: было departmentId
        Revizor revizor = revizorRepository.findById(auditDTO.getRevizorId())
                .orElseThrow(() -> new EntityNotFoundException("Revizor", auditDTO.getRevizorId()));  // Исправлено: было departmentId

        List<Audit> audits = auditRepository.findByDepartmentId(auditDTO.getDepartmentId());
        int nextAuditNumber = audits.size() + 1;

        Audit audit = auditMapper.toEntity(auditDTO, department, employee, revizor);
        audit.setAuditNumber(nextAuditNumber);

        Audit savedAudit = auditRepository.save(audit);
        return auditMapper.toDTO(savedAudit);
    }

    @Override
    public List<AuditDTO> getAllAudits() {
        return auditRepository.findAll().stream()
                .map(auditMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditDTO> getAuditsByIds(Long departmentId, Long employeeId, Long revizorId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department", departmentId);
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee", employeeId);
        }
        if (!revizorRepository.existsById(revizorId)) {
            throw new EntityNotFoundException("Revizor", revizorId);
        }
        // Используем новый метод с фильтрацией по всем трем ID
        return auditRepository.findByDepartmentAndEmployeeAndRevizor(departmentId, employeeId, revizorId)
                .stream()
                .map(auditMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public AuditDTO getAuditById(Long auditId) {
        Audit audit = auditRepository.findById(auditId)
                .orElseThrow(() -> new EntityNotFoundException("Audit", auditId));
        return auditMapper.toDTO(audit);
    }
    @Override
    public AuditDTO updateAudit(Long auditId, AuditDTO updatedAuditDTO) {
        Audit existingAudit = auditRepository.findById(auditId)
                .orElseThrow(() -> new EntityNotFoundException("Audit", auditId));

        Department department = departmentRepository.findById(updatedAuditDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department", updatedAuditDTO.getDepartmentId()));

        Employee employee = employeeRepository.findById(updatedAuditDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", updatedAuditDTO.getEmployeeId()));

        Revizor revizor = revizorRepository.findById(updatedAuditDTO.getRevizorId())
                .orElseThrow(() -> new EntityNotFoundException("Revizor", updatedAuditDTO.getRevizorId()));

        // Обновляем все поля
        existingAudit.setAuditNumber(updatedAuditDTO.getAuditNumber());
        existingAudit.setDepartment(department);
        existingAudit.setDepartmentIdentifier(updatedAuditDTO.getDepartmentIdentifier());
        existingAudit.setEmployee(employee);
        existingAudit.setEmployeeIdentifier(updatedAuditDTO.getEmployeeIdentifier());
        existingAudit.setRevizor(revizor);
        existingAudit.setRevizorIdentifier(updatedAuditDTO.getRevizorIdentifier());
        existingAudit.setTicket(updatedAuditDTO.getTicket());
        existingAudit.setDescription(updatedAuditDTO.getDescription());
        existingAudit.setPurpose(updatedAuditDTO.getPurpose());
        existingAudit.setComments(updatedAuditDTO.getComments());

        Audit updatedAudit = auditRepository.save(existingAudit);
        return auditMapper.toDTO(updatedAudit);
    }

    @Override
    public void deleteAudit(Long auditId) {
        log.info("Deleting audit with id: {}", auditId);
        if (!auditRepository.existsById(auditId)) {
            throw new EntityNotFoundException("Audit", auditId);
        }

        try {
            // Попытка удаления
            auditRepository.deleteById(auditId);
        } catch (Exception e) {
            // Логирование ошибки для дальнейшей отладки
            System.err.println("Ошибка при удалении аудита с id: " + auditId);
            e.printStackTrace();
            throw new RuntimeException("Ошибка при удалении аудита: " + e.getMessage());
        }
    }

    @Override
    public void deleteAuditsByIds(Long departmentId, Long employeeId, Long revizorId) {
        // Проверка существования зависимых сущностей
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department", departmentId);
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee", employeeId);
        }
        if (!revizorRepository.existsById(revizorId)) {
            throw new EntityNotFoundException("Revizor", revizorId);
        }

        List<Audit> audits = auditRepository.findByDepartmentAndEmployeeAndRevizor(
                departmentId, employeeId, revizorId);

        if (!audits.isEmpty()) {
            auditRepository.deleteAll(audits);
        }
    }
}