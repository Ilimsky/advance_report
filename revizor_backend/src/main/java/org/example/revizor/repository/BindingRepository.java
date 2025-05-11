package org.example.revizor.repository;

import org.example.revizor.model.Binding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BindingRepository extends JpaRepository<Binding, Long> {
    List<Binding> findByDepartmentIdAndEmployeeId(Long departmentId, Long employeeId);
}
