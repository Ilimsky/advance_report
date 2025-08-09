package org.example.revizor.mapper;

import lombok.Data;
import org.example.revizor.dto.DepartmentDTO;
import org.example.revizor.model.Audit;
import org.example.revizor.model.Department;
import org.example.revizor.dto.AuditDTO;
import org.example.revizor.model.Employee;
import org.example.revizor.model.Revizor;
import org.springframework.stereotype.Component;

@Component
public class AuditMapper {

    public AuditDTO toDTO(Audit audit) {
        AuditDTO dto = new AuditDTO();
        dto.setId(audit.getId());
        dto.setDepartmentId(audit.getDepartment().getId());
        dto.setEmployeeId(audit.getEmployee().getId());
        dto.setRevizorId(audit.getRevizor().getId());
        dto.setAuditNumber(audit.getAuditNumber());
        dto.setDepartmentIdentifier(audit.getDepartmentIdentifier());
        dto.setEmployeeIdentifier(audit.getEmployeeIdentifier());
        dto.setRevizorIdentifier(audit.getRevizorIdentifier());
        dto.setDateReceived(audit.getDateReceived());
        dto.setTicket(audit.getTicket());
        dto.setDescription(audit.getDescription());
        dto.setPurpose(audit.getPurpose());
        dto.setComments(audit.getComments());
        return dto;
    }

    public Audit toEntity(AuditDTO dto, Department department, Employee employee, Revizor revizor) {
        Audit audit = new Audit();
        audit.setDepartment(department);
        audit.setEmployee(employee);
        audit.setRevizor(revizor);
        audit.setAuditNumber(dto.getAuditNumber());
        audit.setDepartmentIdentifier(dto.getDepartmentIdentifier());
        audit.setEmployeeIdentifier(dto.getEmployeeIdentifier());
        audit.setRevizorIdentifier(dto.getRevizorIdentifier());
        audit.setDateReceived(dto.getDateReceived());
        audit.setTicket(dto.getTicket());
        audit.setDescription(dto.getDescription());
        audit.setPurpose(dto.getPurpose());
        audit.setComments(dto.getComments());
        return audit;
    }
}