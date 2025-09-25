package com.example.backend.service;

import com.example.backend.dto.BindingDTO;

import java.util.List;

public interface BindingService {

    List<BindingDTO> getAllBindings();

    List<BindingDTO> getBindingsByIds(Long departmentId, Long jobId, Long employeeId);

    BindingDTO getBindingById(Long bindingId);
//    BindingDTO createBinding(BindingDTO bindingDTO);

//    BindingDTO updateBinding(Long bindingId, BindingDTO updatedBindingDTO);

//    void deleteBinding(Long bindingId);

//    void deleteBindingsByIds(Long departmentId, Long jobId, Long employeeId);
}
