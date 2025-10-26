package com.example.backend.repository;

import com.example.backend.model.Report;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM Report r WHERE r.department.id = :departmentId")
    @EntityGraph(attributePaths = {"department", "job", "employee", "account"})
    List<Report> findByDepartmentById(@Param("departmentId") Long departmentId);

    @Override
    @EntityGraph(attributePaths = {"department", "job", "employee", "account"})
    Optional<Report> findById(Long id);

    @Query("SELECT MAX(r.reportNumber) FROM Report r WHERE r.department.id = :departmentId AND YEAR(r.dateReceived) = :year")
    Integer findMaxReportNumberByDepartmentAndYear(@Param("departmentId") Long departmentId, @Param("year") int year);

    @Query("SELECT r FROM Report r WHERE r.department.id = :departmentId AND YEAR(r.dateReceived) = :year")
    @EntityGraph(attributePaths = {"department", "job", "employee", "account"})
    List<Report> findByDepartmentIdAndYear(@Param("departmentId") Long departmentId, @Param("year") int year);
}