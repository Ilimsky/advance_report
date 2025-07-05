package org.example.revizor.repository;

import org.example.revizor.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    @Query("SELECT a FROM Audit a WHERE a.department.id = :departmentId")
    List<Audit> findByDepartmentById(@Param("departmentId") Long departmentId);

    @Query("SELECT a FROM Audit a WHERE a.department.id = :departmentId AND a.employee.id = :employeeId AND a.revizor.id = :revizorId")
    List<Audit> findByDepartmentAndEmployeeAndRevizor(
            @Param("departmentId") Long departmentId,
            @Param("employeeId") Long employeeId,
            @Param("revizorId") Long revizorId);
}