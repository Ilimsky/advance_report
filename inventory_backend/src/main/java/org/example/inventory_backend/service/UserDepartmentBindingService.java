package org.example.inventory_backend.service;



import org.example.inventory_backend.dto.UserDepartmentBindingDTO;

import java.util.List;

public interface UserDepartmentBindingService {
    UserDepartmentBindingDTO createBinding(UserDepartmentBindingDTO bindingDTO);
    List<UserDepartmentBindingDTO> getAllBindings();
    List<UserDepartmentBindingDTO> getBindingsByUser(Long userId);
    List<UserDepartmentBindingDTO> getBindingsByDepartment(Long departmentId);
    UserDepartmentBindingDTO getBindingById(Long bindingId);
    UserDepartmentBindingDTO updateBinding(Long bindingId, UserDepartmentBindingDTO updatedBindingDTO);
    void deleteBinding(Long bindingId);
    void deleteBindingByUser(Long userId);
    Long getUserDepartmentId(Long userId);
}