package com.example.backend.repository;

import com.example.backend.model.Binding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BindingRepository extends JpaRepository<Binding, Long> {
    List<Binding> findByDepartmentIdAndJobIdAndEmployeeId(Long departmentId, Long jobId, Long employeeId);

    // Дополнительный метод для поиска по departmentId, например, как в примере для Report
//    @Query("SELECT ed FROM Binding ed WHERE ed.department.id = :departmentId")
//    List<Binding> findByDepartmentById(@Param("departmentId") Long departmentId);
//
//    // Метод для поиска привязок сотрудника по его идентификатору
//    @Query("SELECT ed FROM Binding ed WHERE ed.employee.id = :employeeId")
//    List<Binding> findByEmployeeId(@Param("employeeId") Long employeeId);
//
//    // Метод для поиска привязок по jobId
//    @Query("SELECT ed FROM Binding ed WHERE ed.job.id = :jobId")
//    List<Binding> findByJobId(@Param("jobId") Long jobId);
}
