package org.example.inventory_backend.mapper;

import org.example.inventory_backend.dto.UserDepartmentBindingDTO;
import org.example.inventory_backend.model.Department;
import org.example.inventory_backend.model.User;
import org.example.inventory_backend.model.UserDepartmentBinding;
import org.springframework.stereotype.Component;

@Component
public class UserDepartmentBindingMapper {
    private final UserMapper userMapper;
    private final DepartmentMapper departmentMapper;

    public UserDepartmentBindingMapper(UserMapper userMapper, DepartmentMapper departmentMapper) {
        this.userMapper = userMapper;
        this.departmentMapper = departmentMapper;
    }

    public UserDepartmentBindingDTO toDTO(UserDepartmentBinding binding) {
        UserDepartmentBindingDTO dto = new UserDepartmentBindingDTO();
        dto.setId(binding.getId());
        dto.setUser(userMapper.toDto(binding.getUser()));
        dto.setDepartment(departmentMapper.toDTO(binding.getDepartment()));
        return dto;
    }

    public UserDepartmentBinding toEntity(UserDepartmentBindingDTO dto, Department department, User user) {
        UserDepartmentBinding binding = new UserDepartmentBinding();
        binding.setDepartment(department);
        binding.setUser(user);
        return binding;
    }
}