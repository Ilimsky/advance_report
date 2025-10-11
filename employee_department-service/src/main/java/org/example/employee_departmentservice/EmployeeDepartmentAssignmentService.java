package org.example.employee_departmentservice;


import java.util.List;

public interface EmployeeDepartmentAssignmentService {
    EmployeeDepartmentAssignmentDTO createBinding(EmployeeDepartmentAssignmentDTO bindingDTO);
    List<EmployeeDepartmentAssignmentDTO> getAllBindings();
    List<EmployeeDepartmentAssignmentDTO> getBindingsByIds(Long departmentId, Long employeeId);
    EmployeeDepartmentAssignmentDTO getBindingById(Long bindingId);
    EmployeeDepartmentAssignmentDTO updateBinding(Long bindingId, EmployeeDepartmentAssignmentDTO updatedBindingDTO);
    void deleteBinding(Long bindingId);
    void deleteBindingsByIds(Long departmentId, Long jobId, Long employeeId);
}
