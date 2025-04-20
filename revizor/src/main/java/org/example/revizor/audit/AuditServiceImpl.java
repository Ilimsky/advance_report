package org.example.revizor.audit;

import org.example.revizor.department.Department;
import org.example.revizor.department.DepartmentRepository;
import org.example.revizor.employee.Employee;
import org.example.revizor.employee.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final AuditMapper auditMapper;

    public AuditServiceImpl(AuditRepository auditRepository, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, AuditMapper auditMapper) {
        this.auditRepository = auditRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.auditMapper = auditMapper;
    }

    @Override
    public AuditDTO createAudit(AuditDTO auditDTO) {
        Department department = departmentRepository.findById(auditDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        Employee employee = employeeRepository.findById(auditDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<Audit> audits = auditRepository.findByDepartmentById(auditDTO.getDepartmentId());
        int nextAuditNumber = audits.size() + 1;

        Audit audit = auditMapper.toEntity(auditDTO, department, employee);
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
    public List<AuditDTO> getAuditsByIds(Long departmentId, Long employeeId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new RuntimeException("Department not found");
        }
//        if (!jobRepository.existsById(jobId)) {
//            throw new RuntimeException("Job not found");
//        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found");
        }
//        if (accountRepository.existsById(accountId)) {
//            throw new RuntimeException("Account not found");
//        }
        return auditRepository.findByDepartmentById(departmentId).stream()
                .map(auditMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuditDTO getAuditById(Long auditId) {
        Audit audit = auditRepository.findById(auditId)
                .orElseThrow(() -> new RuntimeException("Audit not found"));
        return auditMapper.toDTO(audit);
    }

    @Override
    public AuditDTO updateAudit(Long auditId, AuditDTO updatedAuditDTO) {
        Audit existingAudit = auditRepository.findById(auditId)
                .orElseThrow(() -> new RuntimeException("Audit not found"));

        Department department = departmentRepository.findById(updatedAuditDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        existingAudit.setAuditNumber(updatedAuditDTO.getAuditNumber());
        existingAudit.setDepartment(department);
        existingAudit.setDepartmentIdentifier(updatedAuditDTO.getDepartmentIdentifier());

        Audit updatedAudit = auditRepository.save(existingAudit);
        return auditMapper.toDTO(updatedAudit);
    }

    @Override
    public void deleteAudit(Long auditId) {
        if (!auditRepository.existsById(auditId)) {
            throw new RuntimeException("Audit not found");
        }
        auditRepository.deleteById(auditId);
    }

    @Override
    public void deleteAuditsByIds(Long departmentId, Long employeeId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new RuntimeException("Department not found");
        }
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found");
        }
        List<Audit> audits = auditRepository.findByDepartmentById(departmentId);
        auditRepository.deleteAll(audits);
    }
}