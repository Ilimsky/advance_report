package org.example.revizor.service;


import org.example.revizor.dto.BindingDTO;

import java.util.List;

public interface BindingService {
    BindingDTO createBinding(BindingDTO bindingDTO);
    List<BindingDTO> getAllBindings();
    List<BindingDTO> getBindingsByIds(Long departmentId, Long jobId, Long employeeId);
    BindingDTO getBindingById(Long bindingId);
    BindingDTO updateBinding(Long bindingId, BindingDTO updatedBindingDTO);
    void deleteBinding(Long bindingId);
    void deleteBindingsByIds(Long departmentId, Long jobId, Long employeeId);
}
