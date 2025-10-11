package org.example.usermanagementservice;

import java.util.List;

public interface UserDepartmentAssignmentService {

    UserDepartmentAssignmentDTO createBinding(UserDepartmentAssignmentDTO bindingDTO);

    List<UserDepartmentAssignmentDTO> getAllBindings();

    List<UserDepartmentAssignmentDTO> getBindingsByUser(Long userId);

    List<UserDepartmentAssignmentDTO> getBindingsByDepartment(Long departmentId);

    UserDepartmentAssignmentDTO getBindingById(Long bindingId);

    UserDepartmentAssignmentDTO updateBinding(Long bindingId, UserDepartmentAssignmentDTO updatedBindingDTO);

    void deleteBinding(Long bindingId);

    void deleteBindingByUser(Long userId);
}