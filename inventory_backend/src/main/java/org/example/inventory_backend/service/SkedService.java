package org.example.inventory_backend.service;

import org.example.inventory_backend.dto.SkedDTO;

import java.util.List;

public interface SkedService {
    SkedDTO createSked(SkedDTO skedDTO);

    List<SkedDTO> getAllSkeds();

    List<SkedDTO> getSkedsByIds(Long departmentId, Long employeeId);

    List<SkedDTO> getSkedsByDepartmentId(Long departmentId);

    SkedDTO getSkedById(Long skedId);

    SkedDTO updateSked(Long skedId, SkedDTO updatedSkedDTO);

    void deleteSked(Long skedId);

    void deleteSkedsByIds(Long departmentId, Long employeeId);
}