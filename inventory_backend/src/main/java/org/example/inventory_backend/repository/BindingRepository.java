package org.example.inventory_backend.repository;

import org.example.inventory_backend.model.Binding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BindingRepository extends JpaRepository<Binding, Long> {
    List<Binding> findByDepartmentIdAndEmployeeId(Long departmentId, Long employeeId);
}
