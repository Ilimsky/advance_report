package com.example.monolith.department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    List<DepartmentDTO> getAllDepartments();

    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    Optional<DepartmentDTO> getDepartmentById(Long id);

    DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO);

    void deleteDepartment(Long id);
}