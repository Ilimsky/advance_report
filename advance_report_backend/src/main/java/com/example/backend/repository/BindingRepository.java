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
}
