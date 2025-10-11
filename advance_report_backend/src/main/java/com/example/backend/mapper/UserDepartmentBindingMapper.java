package com.example.backend.mapper;

import com.example.backend.dto.UserDepartmentBindingDTO;
import com.example.backend.model.Department;
import com.example.backend.model.User;
import com.example.backend.model.UserDepartmentBinding;
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