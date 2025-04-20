package org.example.revizor.audit;

import org.example.revizor.department.Department;
import org.example.revizor.employee.Employee;
import org.springframework.stereotype.Component;

@Component
public class AuditMapper {

    public AuditDTO toDTO(Audit audit) {
        AuditDTO dto = new AuditDTO();
        dto.setId(audit.getId());
        dto.setDepartmentId(audit.getDepartment().getId());
        dto.setEmployeeId(audit.getEmployee().getId());
        dto.setAuditNumber(audit.getAuditNumber());
        dto.setDepartmentIdentifier(audit.getDepartmentIdentifier());
        dto.setEmployeeIdentifier(audit.getEmployeeIdentifier());
        dto.setDateReceived(audit.getDateReceived());
        dto.setPurpose(audit.getPurpose());
        dto.setComments(audit.getComments());
        return dto;
    }

    public Audit toEntity(AuditDTO dto, Department department, Employee employee) {
        Audit audit = new Audit();
        audit.setDepartment(department);
        audit.setEmployee(employee);
        audit.setAuditNumber(dto.getAuditNumber());
        audit.setDepartmentIdentifier(dto.getDepartmentIdentifier());
        audit.setEmployeeIdentifier(dto.getEmployeeIdentifier());
        audit.setDateReceived(dto.getDateReceived());
        audit.setPurpose(dto.getPurpose());
        audit.setComments(dto.getComments());
        return audit;
    }
}