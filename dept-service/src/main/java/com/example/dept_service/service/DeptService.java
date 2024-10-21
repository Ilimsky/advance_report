package com.example.dept_service.service;

import java.util.List;
import java.util.Optional;

import com.example.dept_service.dto.DeptDTO;

public interface DeptService {

    DeptDTO createDept(DeptDTO deptDTO);

    List<DeptDTO> getAllDepts();

    Optional<DeptDTO> getDeptById(Long id);

    DeptDTO updateDept(Long id, DeptDTO deptDTO);

    void delete(Long id);

}
