package com.example.reportservice.repo;

import com.example.reportservice.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByDepartmentIdentifier(Long departmentIdentifier);
}
