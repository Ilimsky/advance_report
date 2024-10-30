package com.example.departmentservice.service;

import com.example.departmentservice.dto.DepartmentDTO;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    List<DepartmentDTO> getAllDepartments();

    Optional<DepartmentDTO> getDepartmentById(Long id);

    DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO);

    void delete(Long id);

}
