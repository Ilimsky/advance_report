package org.example.reference_backend.repository;

import org.example.reference_backend.model.EmployeeDepartmentAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDepartmentAssignmentRepository extends JpaRepository<EmployeeDepartmentAssignment, Long> {
    List<EmployeeDepartmentAssignment> findByDepartmentIdAndEmployeeId(Long departmentId, Long employeeId);
}
