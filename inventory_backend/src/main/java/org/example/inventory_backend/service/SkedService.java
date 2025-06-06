package org.example.inventory_backend.service;

import org.example.inventory_backend.dto.SkedDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SkedService {
    SkedDTO createSked(SkedDTO skedDTO);
    List<SkedDTO> getAllSkeds();
    List<SkedDTO> getSkedsByDepartmentId(Long departmentId);
    SkedDTO getSkedById(Long skedId);
    SkedDTO updateSked(Long skedId, SkedDTO updatedSkedDTO);
    void deleteSked(Long skedId);
    Page<SkedDTO> getAllSkedsPaged(Pageable pageable);
    Page<SkedDTO> getSkedsByDepartmentIdPaged(Long departmentId, Pageable pageable);

//    void deleteSkedsByIds(Long departmentId, Long employeeId);
//    List<SkedDTO> getSkedsByIds(Long departmentId, Long employeeId);
}