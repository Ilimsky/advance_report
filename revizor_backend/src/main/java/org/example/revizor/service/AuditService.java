package org.example.revizor.service;

import org.example.revizor.dto.AuditDTO;
import org.example.revizor.dto.DepartmentDTO;

import java.util.List;

public interface AuditService{
    AuditDTO createAudit(AuditDTO auditDTO);
    List<AuditDTO> getAllAudits();
    List<AuditDTO> getAuditsByIds(Long departmentId, Long employeeId, Long revizorId);

    List<AuditDTO> getAuditsByDepartmentId(Long departmentId);
    AuditDTO getAuditById(Long auditId);
    AuditDTO updateAudit(Long auditId, AuditDTO updatedAuditDTO);
    void deleteAudit(Long auditId);
    void deleteAuditsByIds(Long departmentId, Long employeeId, Long revizorId);
}