package org.example.reference_backend.mapper;

import org.example.reference_backend.model.User;
import org.example.reference_backend.dto.UserDepartmentAssignmentDTO;
import org.example.reference_backend.model.Department;
import org.example.reference_backend.model.UserDepartmentAssignment;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {UserMapper.class, DepartmentMapper.class})
public interface UserDepartmentAssignmentMapper {


    UserDepartmentAssignmentDTO toDTO(UserDepartmentAssignment binding);

    @Mapping(target = "id", ignore = true)
    UserDepartmentAssignment toEntity(UserDepartmentAssignmentDTO dto,
                                      @Context Department department,
                                      @Context User user);
}